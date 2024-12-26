#!/bin/bash

# 스크립트 실행 권한 확인
if [ "$(id -u)" -ne 0 ]; then
  echo "Please run this script as root or use sudo."
  exit 1
fi

# Auditd 설치
echo "Installing Auditd..."
if ! dpkg -l | grep -q auditd; then
  sudo apt update && sudo apt install -y auditd
  sudo systemctl enable auditd
  sudo systemctl start auditd
  echo "Auditd installation and configuration complete."
else
  echo "Auditd is already installed."
fi

# Auditbeat 설치
AUDITBEAT_VERSION="8.17.0"
AUDITBEAT_PACKAGE="auditbeat-${AUDITBEAT_VERSION}-amd64.deb"
if ! dpkg -l | grep -q auditbeat; then
  echo "Installing Auditbeat..."
  curl -L -O https://artifacts.elastic.co/downloads/beats/auditbeat/$AUDITBEAT_PACKAGE
  sudo dpkg -i $AUDITBEAT_PACKAGE
  rm -rf $AUDITBEAT_PACKAGE
  echo "Auditbeat installation complete."
else
  echo "Auditbeat is already installed."
fi

# Auditbeat 설정 파일 경로
CONFIG_FILE="/etc/auditbeat/auditbeat.yml"

# Logstash 출력 설정 추가
echo "Configuring Auditbeat to send logs to Logstash..."
sudo tee $CONFIG_FILE > /dev/null <<EOL
# ============================== Auditbeat Configuration ==============================

# ------------------------------ Logstash Output -------------------------------
output.logstash:
  # The Logstash hosts
  hosts: ["10.11.70.42:5044"]

# ================================= Processors =================================
EOL

# Auditd 규칙 파일 경로
RULES_FILE="/etc/audit/rules.d/audit.rules"

# 규칙 파일 디렉토리 확인 및 생성
if [ ! -d "/etc/audit/rules.d" ]; then
  sudo mkdir -p /etc/audit/rules.d
fi

# 추가할 규칙
RULES=(
  "-a always,exit -F arch=b64 -S execve -F key=alert_bot"
  "-a always,exit -F arch=b64 -S execve -F dir=/var/log -F key=login_failures"
)

echo "Adding rules to $RULES_FILE..."

# Backup 기존 규칙 파일
if [ -f "$RULES_FILE" ]; then
  sudo cp "$RULES_FILE" "${RULES_FILE}.backup"
  echo "Backup created at ${RULES_FILE}.backup"
fi

# 규칙 추가
for RULE in "${RULES[@]}"; do
  if ! grep -Fxq "$RULE" "$RULES_FILE"; then
    echo "$RULE" | sudo tee -a "$RULES_FILE" > /dev/null
    echo "Added rule: $RULE"
  else
    echo "Rule already exists: $RULE"
  fi
done

# Auditbeat 서비스 활성화 및 시작
echo "Enabling and starting Auditbeat service..."
sudo systemctl enable auditbeat
sudo systemctl start auditbeat

# 설정 완료 메시지
echo "Auditbeat has been successfully configured to send logs to Logstash at 10.11.70.42:5044."
echo "Use the following command to check its status:"
echo "  sudo systemctl status auditbeat"

