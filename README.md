# BigData-App-With-hadoop-World-temperature-study
Aplication d'étude des températures observées sur toute la planete crée avec Hadoop sur cloudera. Les variations de la température seront observées par l'utilisateur à partir d'un tableau dans une interface en fonction de plusieurs critères qu'il va donner à partir d'une interface.
# Methodologie 
Recuperer les parametres du formulaire remplis par l'utilisateur, les filtrer ensuite enregistrer les données agregées dans Mysql avant de les recuperer et les afficher dans un tableau en php.
Les données à traiter viennent de l'internet du site https://www.ncdc.noaa.gov/.  Ils seront  importées dans l'HDFS, dans le Système de Fichiers distribués d'Adobe. A partir de là, on va faire un import en MapReduce Dans HBase et ça sera HBase qui fera l'agrégation des données et donc, le groupe, selon les dimensions demandées, pour mettre les résultats dans MySQL. MySQL sera alors interrogé par PHP pour faire les traitements et l'affichage graphique directement en web.
 
