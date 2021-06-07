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

/** List cell for the transactions view*/
public class ReprTransactionListCell extends ListCell<Transaction> {
    private HBox content;
    // Defining the Items I want to display in the list cell
    private Text NewBalance;
    private Text Amount;
    private Text Date;
    private Text heading;
    private Separator separator ;
    public ReprTransactionListCell() {
        super();
        // Instantiating the local variables
        Date = new Text();
        Amount = new Text();
        heading = new Text();
        NewBalance = new Text();
        separator = new Separator();
        
        //Assigning values
        separator.setHalignment(HPos.LEFT);
        NewBalance.setId("textBox");
        Amount.setId("textBox");
        Date.setId("textBox");
        heading.setId("textBox");
        heading.setText("Transaction");
        heading.setTextAlignment(TextAlignment.CENTER);
        heading.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        
        //Adding the UI elements
        VBox vBox = new VBox(heading,Date,NewBalance, Amount,separator);
        content = new HBox(vBox);
        content.setSpacing(10);
    }

    @Override
    protected void updateItem(Transaction item, boolean empty) {
        super.updateItem(item, empty);
        if (item != null && !empty) { // <== test for null item and empty parameter
            NewBalance.setText(String.format("The balance after the transaction : %s", item.getNewBalance()));
            Amount.setText(String.format("Transacted amount : %s",item.getAmount()));
            Date.setText(String.format("Tansaction at : %s",item.getDate()));
            setGraphic(content);
        } else {
            setGraphic(null);
        }
    }
}