commande pour run le programme : javac Main.java && java Main

Concernant les actions a faire : je suis parti du principe que d abord on indique l action a faire et puis les arguments


TODO : de nombreux cas ne sont pas géré; exemple : que se passe t il si je suis louis et que j ajoute comme ami louis (moi meme) ? 
comment gérer deux utilisateur qui ont le meme nom ? par exemple si je m appelle louis A et que je veu ajouter louis B mais qu on a le meme username comment faire ?

comment gérer les discussions ? faut il créer une DB pour les discussions ? ou les mettre dans dbutilisateurs ? ou tout simplement stocker les discussions directement dans user et donc si un user envoie un message a X autres user, alors X+1 users vont etre update avec leur discussion 
Pour l instant j ai créé une autre db qui contient toute les discussions de tt les utilisateurs (peut etre pas la meilleure solution)

Comment receive_message se fait called ? je ne sais pas si quand je send_message je dois call receive sur les autres utilisateurs 
car apres tout la simulation simule un seul utilisateur ? 

pour checkmessage je n ai pas encore fait le cas ou le meme message pourrait se retrouver dans plusieurs conversations
aussi peut etre mettre un limiteur de check ? par exemple si on met limite=5 et qu on cherche le mot "ok" seulement 5 ok peuvent apparaitre

autres Hypotheses émises : 
-il est impossible de contacter un utilisateur qui ne fais pas partie de la liste d amis

ATTENTION : user_id ne veut pas dire que la liste d users dans usersdb sera sorted : si un user se fait delete, la liste ne sera plus dans le bon ordre (ex : si user_id=1 se fait delete alors users = [0,2,3]

