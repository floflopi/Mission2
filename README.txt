commande pour run le programme : make run

Comment receive_message se fait called ? je ne sais pas si quand je send_message je dois call receive sur les autres utilisateurs 
car apres tout la simulation simule un seul utilisateur ? 

autres Hypotheses émises : 
-il est impossible de contacter un utilisateur qui ne fais pas partie de la liste d amis (sauf dans un groupe)
-deux utilisateurs ne peuvent pas avoir le meme nom
-un utilisateur ne peut pas s ajouter lui meme en tant qu ami et ne peut pas faire de discussion avec lui meme
-pour créer un groupe, il faut etre ami avec tt les gens du groupe

ATTENTION : user_id ne veut pas dire que la liste d users dans usersdb sera sorted : si un user se fait delete, la liste ne sera plus dans le bon ordre (ex : si user_id=1 se fait delete alors users = [0,2,3]

TODO : toute les features ont été implémentée. ce qu il reste a faire c est clean le code et faire les cas extremes (cas limite)
par exemple essayer d utiliser plus de design pattern, enlever du duplicate code etc

explication pq créer une classe fichier : pour l instant nous avons supposé que un fichier aura le meme comportement
peu importe son contenu (image,vidéo). Lorsque nous devrons implémenter l'UI, il suffira juste de remove cette classe et d'implémenter
deux autres classe qui hérite de message dans le cas ou différent fichiers ont différents comportement.