package source;

import javafx.geometry.HPos;
import javafx.scene.control.ListCell;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/** List cell for the users view*/
public class ReprUserListCell extends ListCell<Customer> {
    private HBox content;
    // Defining the Items I want to display in the list cell
    private Text name;
    private Text socialSec;
    private Text heading;
    private Separator separator ;
    public ReprUserListCell() {
        super();
        
        // Instantiating the local variables
        name = new Text();
        heading = new Text();
        socialSec = new Text();
        separator = new Separator();
        
        // Assigning the values
        name.setId("textBox");
        socialSec.setId("textBox");
        separator.setHalignment(HPos.LEFT);
        heading.setText("Customer");
        heading.setId("textBox");
        heading.setTextAlignment(TextAlignment.CENTER);
        heading.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        // Adding the UI elements
        VBox vBox = new VBox(heading,name, socialSec,separator);
        content = new HBox(vBox);
        content.setSpacing(10);
    }

    @Override
    protected void updateItem(Customer item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null && !empty) { 
            name.setText(String.format("Name : %s", item.getName()));
            socialSec.setText(String.format("Social security number : %s",item.getSocialSecurity()));
            setGraphic(content);
        } else {
            setGraphic(null);
        }
    }
}