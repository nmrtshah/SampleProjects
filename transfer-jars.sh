
sourcePath="/opt/apache-tomcat1/webapps/finstudio"
currTimeStamp=$(date +%d%m%Y%H%M%S)

for i in `ls -d /opt/*tomcat*`
do

	if [ -d "$i/webapps" ] 
	then

		echo -n "Would You Like to Transfer in $i ? (y/n) :"
		read ans1

		if [ "$ans1" = "y" ] 
		then

			cd $sourcePath
			for j in `ls *.jar`
			do
				sourceMD5=`md5sum $sourcePath/$j | awk '{print $1}'`
				if [ -f "$i/lib/$j" ]
				then
                        		destiMD5=`md5sum $i/lib/$j | awk '{print $1}'`
				else
					echo "Jar Not Exist"
					destiMD5="NA";
				fi

				if [ "$sourceMD5" = "$destiMD5" ]; then
	                                echo "No Change in $j"
				else
					echo -n "Transfer Jar $j ? (y/n) :"
					read ans2

					if [ "$ans2" = "y" ] 
					then

						if [ -f "$i/lib/$j" ]
						then
							# if bkp dir not exist, create one
							if [ -d "$i/old_lib" ] 
							then
								echo "Backup Dir Exist"
							else
								echo "Backup Dir Created"
								mkdir $i/old_lib								
							fi
							cp $i/lib/$j $i/old_lib/$j.$currTimeStamp
							echo "Backup Taken"
						fi
						cp $sourcePath/$j $i/lib/$j
						echo "Transferred"
					else
						echo "Ignored"
					fi
				fi
			done
		else
			echo "Ignoring Tomcat Path : $i"
		fi
	fi
done

chmod 777 /opt/*tomcat*/lib/*.jar
find /opt/*tomcat*/lib -name "*.jar" -mtime -2 -exec ls -l {} \;
