<?php

$station = $_REQUEST['station'];
$measure = $_REQUEST['measure'];
$dim1 = $_REQUEST['dim1'];
$dim2 = $_REQUEST['dim2'];
$operator = $_REQUEST['operator'];

$xml = <<<EOT
<configuration>
	<property>
		<name>user.name</name>
		<value>cloudera</value>
	</property>
	<property>
		<name>nameNode</name>
		<value>hdfs://quickstart.cloudera:8020</value>
	</property>
	<property>
		<name>jobTracker</name>
		<value>quickstart.cloudera:8032</value>
	</property>
	<property>
		<name>oozie.wf.application.path</name>
		<value>\${nameNode}/app</value>
	</property>
	<property>
		<name>oozie.use.system.libpath</name>
		<value>true</value>
	</property>
	<property>
		<name>processDir</name>
		<value>\${nameNode}/data/aggregated_data/</value>
	</property>
	<property>
		<name>station</name>
		<value>$station</value>
	</property>
	<property>
		<name>measure</name>
		<value>$measure</value>
	</property>
	<property>
		<name>dim1</name>
		<value>$dim1</value>
	</property>
	<property>
		<name>dim2</name>
		<value>$dim2</value>
	</property>
	<property>
		<name>operator</name>
		<value>$operator</value>
	</property>
</configuration>
EOT;


$ch = curl_init();
curl_setopt($ch, CURLOPT_URL, "http://quickstart.cloudera:11000/oozie/v2/jobs?action=start");
curl_setopt($ch, CURLOPT_POST, 1);
curl_setopt($ch, CURLOPT_POSTFIELDS, $xml);
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
curl_setopt($ch, CURLOPT_HTTPHEADER, ["Content-Type: application/xml"]);

$json = curl_exec ($ch);
curl_close ($ch);

$id = json_decode($json)->id;

header('Location: /result.php?job=' . $id);


