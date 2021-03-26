import java.io.*;
public interface User_Data_Base{
    public class Linked_List {

        Node head; // head of list
        static int counter=0;
        static int[] conversion_strings_to_ints_on_a_array=null;

        static class Node {

            String user_Saved_Data;
            Node next;


            public Node(String _userSavedData) {
                this.user_Saved_Data = _userSavedData;
                next = null;
            }
        }

        public static void save(Linked_List list, String _userSavedData) {
            Node new_node = new Node(_userSavedData);
            new_node.next = null;
            if (list.head == null) {
                list.head = new_node;

                int array=conversion_strings_to_ints_on_a_array[counter];
                counter++;

            } else {
                Node last = list.head;
                while (last.next != null) {
                    last = last.next;

                }
                last.next = new_node;
                counter++;
            }


        }

        public static void delete_Specific_Favorite(Linked_List list, int user_Article_NUM) {
            //3 cases
            //1 first
            //2 not first
            //3 no there
        }

        public static void delete_recent_Favorite(Linked_List list) {
            Node last = list.head;
            while (last.next != null) {
                last = last.next;
            }
            last = null;
        }
        public static void whats_My_List(Linked_List list){

        }

    }
}