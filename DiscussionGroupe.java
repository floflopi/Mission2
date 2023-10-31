import java.util.ArrayList;

/*
 * discussion de groupe contient un attribut en plus qui est admin (créateur de la discussion)
 *
 */
public class DiscussionGroupe extends Discussion {
    private int admin_id;
    public DiscussionGroupe(ArrayList<Integer> members_id,int admin_id){
        super(members_id,false);
        this.admin_id = admin_id;
    }
    public int get_admin_id(){
        return admin_id;
    }
}