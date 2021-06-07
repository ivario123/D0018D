package ivajns9;

import javafx.geometry.HPos;
import javafx.scene.control.ListCell;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
/** List cell for the account view*/
public class ReprAccountListCell extends ListCell<Account> {
    private HBox content;
    // Defining the Items I want to display in the list cell
    private Text accountType;
    private Text accountNumber;
    private Text balance;
    private Text heading;
    private Separator separator ;

    public ReprAccountListCell() {
        super();
        //Initiating the local variables
        balance = new Text();
        heading = new Text();
        accountType = new Text();
        accountNumber = new Text();
        separator = new Separator();
        //Assigning the values
        balance.setId("textBox");
        accountType.setId("textBox");
        accountNumber.setId("textBox");
        separator.setHalignment(HPos.LEFT);
        heading.setId("textBox");
        heading.setText("Account");
        heading.setTextAlignment(TextAlignment.CENTER);
        heading.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        
        // Adding the UI elements to the content view
        VBox vBox = new VBox(heading,accountType, accountNumber,balance,separator);
        content = new HBox(vBox);
        content.setSpacing(10);
    }

    @Override
    protected void updateItem(Account item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null && !empty) {
        	//Setting the values
        	accountType.setText(String.format("Account type : %s",item.getAccountType()));
            accountNumber.setText(String.format("Account number : %d",item.getAccNum()));
            balance.setText(String.format("Account Balance : %.3f kr", item.getAccountBalance()));
            setGraphic(content);
        } else {
            setGraphic(null);
        }
    }
}
