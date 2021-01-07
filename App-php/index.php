<?php

$mysql = new mysqli("localhost", "root", "cloudera", "monapp");
$result = $mysql->query("SELECT DISTINCT job FROM results");

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

<h2>Nouvelle requête</h2>
<form action="/request.php" method="GET" />

<label for="dim1">Dimension 1</label>
<select name="dim1" id="dim1">
  <option>STATION</option>
  <option>MEASURE</option>
  <option>LATITUDE</option>
  <option>LONGITUDE</option>
  <option>MONTH</option>
  <option>WEEK</option>
  <option>YEAR</option>
  <option>VOID</option>
</select>

<label for="dim2">Dimension 2</label>
<select name="dim2" id="dim2">
  <option>STATION</option>
  <option>MEASURE</option>
  <option>LATITUDE</option>
  <option>LONGITUDE</option>
  <option>MONTH</option>
  <option>WEEK</option>
  <option>YEAR</option>
  <option>VOID</option>
</select>

<label for="measure">Mesure</label>
<select name="measure" id="measure">
  <option value="">Toutes les mesures</option>
  <option>TAVG</option>
  <option>TMIN</option>
  <option>TMAX</option>
</select>

<label for="operator">Mesure</label>
<select name="operator" id="operator">
  <option>AVG</option>
  <option>SUM</option>
  <option>MIN</option>
  <option>MAX</option>
  <option>RANGE</option>
</select>

<label for="station">Station</label>
<input type=text" name="station" id="station" />

<input type="submit" class="btn btn-default" />

</form>


<h2>Requêtes déjà exécutées</h2>
<ul>
<?php while ($request = $result->fetch_assoc()) { ?>
  <li><a href="result.php?job=<?= $request['job'] ?>"><?= $request['job'] ?></a></li>
<?php } ?>
</ul>


</body>
</html>

