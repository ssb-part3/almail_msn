설치 파일 
/volume2/deploy/v3load-3.1-1.x86_64.rpm 120메가
이거를  서버 아무데나 올림

설치 방법 
rpm -Uvh v3load-3.1-1.x86_64.rpm 

제거 방법
rpm -e v3load-3.1-1.x86_64.rpm 

설치파일들

#v3 engine
/usr/local/v3engine

v3load
/www/v3load3

설치시 백신설치 하고 업데이트 파일도 자동으로 설치됨
백신기동까지 완료된 상태로 설치가 종료됨

발급된 시리얼을 아래 파일에 보면 키 넣는 부분에 넣어야 하는데
식약청 발급키를 걍 넣어 버렸음..

다음부터는 설치후 키를 변경해서 설치완료 할것
/usr/local/v3engine/v3daemon/option.cfg

/www/v3load3/v3load3.sh /tmp/kkk /tmp/kkk

위처럼 실행해서 에러가 없으면 정상설치된것이고
/data/log/v3check.log

${LOG_DIR}/${HOSTNAME}_${DAY_HOUR}.log 파일에 로그가 쌓이니
참조하면 될듯..

그럼 수고하세요.

	