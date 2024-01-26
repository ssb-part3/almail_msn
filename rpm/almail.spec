%define name alm
%define ver 1.0
%define rel 7
%define namever %{name}%{ver}
Summary: Notify File to Email
Name: %{name}
Version: %{ver}
Release: %{rel}
License: Commercial
Group: Mail/Daemons
Source: %{namever}-src.tar.gz
BuildRoot: /var/tmp/%{name}-buildroot
%description
Approve Info Send to Email solutions. SQISoft Co.ltd

%prep

%install
if [ -d $RPM_BUILD_ROOT ]; then rm -r $RPM_BUILD_ROOT; fi

mkdir -p $RPM_BUILD_ROOT/www
mkdir -p $RPM_BUILD_ROOT/www/alm_v%{ver}
mkdir -p $RPM_BUILD_ROOT/www/alm_v%{ver}/install

install /home/almail/almv%{ver}/alm-%{ver}-src.tar.gz	$RPM_BUILD_ROOT/www/alm_v%{ver}/install/alm-%{ver}-src.tar.gz

%clean
if [ -d $RPM_BUILD_ROOT ]; then rm -r $RPM_BUILD_ROOT ; fi

%files

/www/alm_v%{ver}/

%post

ln -s /www/alm_v%{ver} /www/alm

cd /www/alm_v%{ver}/
tar xfz install/alm-%{ver}-src.tar.gz

cd /www/alm_v%{ver}/ext  
tar xvf jre1.8.0_11.tar
ln -s jre1.8.0_11 jre 

cp -p /www/alm_v%{ver}/bat/almd /etc/init.d/almd
cp -p /www/alm_v%{ver}/src/log4j.properties /www/alm_v%{ver}/bin/log4j.properties

chkconfig --add almd

chmod 755 /www/alm_v%{ver}/bat/almd
chmod 755 /etc/init.d/almd
chmod 755 /www/alm_v%{ver}/almail_start.sh
chmod 755 /www/alm_v%{ver}/almail_status.sh
chmod 755 /www/alm_v%{ver}/almail_stop.sh
chmod 755 /www/alm_v%{ver}/almail_restart.sh
chmod 755 /www/alm_v%{ver}/bat/*

/bin/rm -rf /www/alm_v%{ver}/src
/bin/rm -rf /www/alm_v%{ver}/rpm

%postun

service almd stop
chkconfig --del almd
rm -f /etc/init.d/almd

rm -rf /www/alm
rm -rf /www/alm_v%{ver}

