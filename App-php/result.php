<?php
error_reporting(-1);

$job = $_REQUEST['job'];
$mysql = new mysqli("localhost", "root", "cloudera", "monapp");
$result = $mysql->query("SELECT * FROM results WHERE job='" . $mysql->escape_string($job) . "' ORDER BY dim1, dim2");

$values = [];
$dim1 = [];
$dim2 = [];
while ($row = $result->fetch_assoc()) {
	if (!in_array($row['dim1'], $dim1)) {
		$dim1[] = $row['dim1'];
	}
	if (!in_array($row['dim2'], $dim2)) {
		$dim2[] = $row['dim2'];
	}
	$values[array_search($row['dim1'], $dim1)][array_search($row['dim2'], $dim2)] = $row['value'];
}

?>

<html>

<head>
<title>MyApp</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
</head>
<body class="container">
<h1>MyApp</h1>

<?php if (empty($values)) { ?>

<p>Calcul en cours, veuillez patienter...</p>
<script>
setTimeout(function(){
   window.location.reload(1);
}, 5000);
</script>


<?php } else { ?>

<table id="table" class="table">

<tr>
	<th></th>
<?php for ($i=0; $i<sizeof($dim1); $i++) { ?>
	<th><?= $dim1[$i] ?></th>
<?php } ?>
</tr>	

<?php for ($j=0; $j<sizeof($dim2); $j++) { ?>
<tr>
	<th> <?=$dim2[$j] ?></th>
	<?php for ($i=0; $i<sizeof($dim1); $i++) { ?>
		<td><?= $values[$i][$j] ?? "" ?></td>
	<?php } ?>
</tr>
<?php } ?>

</table>

<?php } ?>

</body>
</html>

