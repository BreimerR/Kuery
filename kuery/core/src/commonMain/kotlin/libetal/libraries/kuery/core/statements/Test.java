package libetal.libraries.kuery.core.statements;

import java.util.ArrayList;

class Main {

    static ArrayList<String> selectAll() {
        ArrayList<ArrayList<String>> selectedItems = new ArrayList<>();
        .....

        while(rs.next()){
            ArrayList<String> rowResult = new ArrayList<String>();

            rowResult.add(rs.getString("name"));
            rowResult.add(rs.getString("obstacles"));
            rowResult.add(rs.getString("position"));
            rowResult.add(rs.getInt("size"));

            selectedItems.add(rowResult)
        }


        ....

        return selectedItems
    }


    static void main() {
        ArrayList<ArrayList<String>> results = selectAll();

        ArrayList<String> individual = results.get(0);


        System.out.println()
    }
}