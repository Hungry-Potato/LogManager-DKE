import os
import json
import paramiko
import requests
from datetime import datetime, timedelta
from airflow import DAG
from airflow.operators.python_operator import PythonOperator

# Discord Webhook URL
DISCORD_WEBHOOK_URL = ""

def alert_to_discord(name, ip, port):
    message = f"```ansi\n{u'\u001B'}[1;36;40m{name}{u'\u001B'}[0m{u'\u001B'}[40m({ip}:{port})가 꺼져있습니다.{u'\u001B'}[0m```"
    
    payload = {
        "content": message,
    }
    try:
        response = requests.post(DISCORD_WEBHOOK_URL, json=payload)
        if response.status_code == 204:
            print("Discord alert sent successfully")
        else:
            print(f"Failed to send alert: {response.status_code}")
    except Exception as e:
        print(f"Error sending Discord alert: {e}")

def check_server_status_ssh(server_ip, user_name, port, name):
    ssh_client = paramiko.SSHClient()
    ssh_client.set_missing_host_key_policy(paramiko.AutoAddPolicy())
    private_key_path = os.path.expanduser("/opt/airflow/ssh/id_rsa")  # Expand user path
    try:
        ssh_client.connect(server_ip, port=port, username=user_name, key_filename=private_key_path)
        stdin, stdout, stderr = ssh_client.exec_command('uptime')
        output = stdout.read().decode('utf-8', 'ignore')
        if "load average" in output:
            print(f"Server {name} is up")
            return f"Server {name} is up"
        else:
            raise Exception(f"Server {name} is down")
    except Exception as e:
        error_message = f"Server {name} is down: {str(e)}"
        alert_to_discord(name, server_ip, port)
        return error_message
    finally:
        ssh_client.close()

def check_server():
    dag_dir = os.path.dirname(os.path.abspath(__file__))  # DAG 파일이 있는 디렉토리
    json_file_path = os.path.join(dag_dir, 'servers.json')
    
    with open(json_file_path, 'r') as json_file:
        servers = json.load(json_file)

    for i in range(len(servers)):
        check_server_status_ssh(servers[i]['ip'], servers[i]['username'], servers[i]['port'], servers[i]['name'])

default_args = {
    'owner': 'airflow',
    'start_date': datetime(2024, 12, 16),
    'retries': 1,
}


dag = DAG(
    'check_server_status',
    default_args=default_args,
    description='매분 서버 확인후 다운 됐을 시, Discord로 알람',
    schedule_interval=timedelta(minutes=1),
    catchup=False,
)

check_server_status_task = PythonOperator(
    task_id='check_server_status',
    python_callable=check_server,
    dag=dag,
)

check_server_status_task


