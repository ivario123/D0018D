package source;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

/**
 * The main class is responsible for all of the UI elements

 */
public class Main extends Application {

	
	public IO IOstream;
	/** Declaring class variables */
	/** The bank logic */
	private BankLogic logic;
	/** The list view containing all of the users*/
	private ListView<Customer> users;
	/** The list view containing the users accounts*/
	private ListView<Account> accounts;
	/** The list view containing the accounts transactions*/
	private ListView<Transaction> transactions;
	/** The text area at the bottom left of the screen displaying instructions */
	private TextArea console;
	/** The percentage of available screen space that the console occupies*/
	private double consoleHeight = .2;
	/** The menu for Account actions*/
    Menu AccountMenu;
    /** The menu for transaction actions */
    Menu TransactionMenu;
    /** The menu for user actions */
    Menu UserMenu;
    /** The menu for file actions */
    Menu FileMenu;
    /** The width of the list views as percentage of screen size*/
	double w = .33333333333;
	/** The list views vertical padding */
	double y = .03;
	/** Start function is responsible for instantiating all the elements */
	@Override
	public void start(Stage primaryStage) {
		
		
		try {
			IOstream = new IO();
			//====================================================================================
			//================================ Scene settings ====================================
			//====================================================================================
			Pane root = (Pane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
			Scene scene = new Scene(root,400,400);
			logic = new BankLogic();
			
			primaryStage.setFullScreen(true);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			

			//====================================================================================
			//============================== Menu bar settings ===================================
			//====================================================================================
			

			/** Menu bar instance */
	        MenuBar mb = new MenuBar();
	        
	        
	        // Adding the menus
	        FileMenu = new Menu("FileMenu");
	        UserMenu = new Menu("User");
	        AccountMenu = new Menu("Account");
	        TransactionMenu = new Menu("Transaction");
			
	        // =================
	        // Adding menu items
	        // =================
	        
			Button exitProgram = new Button("Exit");
			exitProgram.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) { Exit(primaryStage); }

				
			});
			

	        MenuItem saveFile= new MenuItem("Save to file");
	        saveFile.setOnAction(e -> { SaveFile(primaryStage); });
	        
	        MenuItem loadFile= new MenuItem("Load from file");
	        loadFile.setOnAction(e -> { LoadFile(primaryStage); });
	        
	        MenuItem addUser = new MenuItem("Add a new user");
	        addUser.setOnAction(e -> { addUser(primaryStage); });
	        
	        
	        MenuItem removeUser = new MenuItem("Remove a user");
	        removeUser.setOnAction(e -> { removeUser(primaryStage); });
	        
	        
	        MenuItem addAccount = new MenuItem("Add a new account");
	        addAccount.setOnAction(e -> { addAccount(primaryStage); });
	        
	        
	        MenuItem removeAccount = new MenuItem("Remove the selected account");
	        removeAccount.setOnAction(e -> { removeAccount(primaryStage); });
	        
	        
	        MenuItem changeName = new MenuItem("Change the selected users name");
	        changeName.setOnAction(e -> { ChangeName(primaryStage); });
	        
	        
	        MenuItem Export = new MenuItem("Export the selected transaction to a file");
	        Export.setOnAction(e->{ ExportTransaction(primaryStage); });
	        
	        MenuItem Withdraw = new MenuItem("Withdraw from the selected account");
	        Withdraw.setOnAction(e->{ withdrawPop(primaryStage); });
	        
	        MenuItem Deposit = new MenuItem("Deposit to the selected account");
	        Deposit.setOnAction(e->{ depositPop(primaryStage);  });
	        
	        
	        
	        
	        // ==================================
	        // Adding the menu items to the menus
	        // ==================================
	        
	        FileMenu.getItems().addAll(saveFile,loadFile);
	        AccountMenu.getItems().addAll(addAccount,removeAccount);
	        UserMenu.getItems().addAll(addUser,changeName,removeUser);
	        TransactionMenu.getItems().addAll(Withdraw,Deposit,Export);
	        
	        // ================================
	        // Adding the menus to the menu bar
	        // ================================
	        mb.getMenus().addAll(FileMenu,UserMenu,AccountMenu,TransactionMenu);
	        
	        // =======================================
	        // Adding the menu bar and the exit button
	        // =======================================
	        Region spacer = new Region();
	        spacer.getStyleClass().add("menu-bar");
	        HBox.setHgrow(spacer, Priority.SOMETIMES);
	        HBox menubar = new HBox(mb, spacer, exitProgram);
	        menubar.setPrefWidth(scene.getWidth());
			root.getChildren().add(menubar);
			
			
			
			
			


			//====================================================================================
			//============================== List view settings ==================================
			//====================================================================================
			
			
			// =========================
			// Initiating the list views
			// =========================
			users = new ListView<Customer>();
			accounts = new ListView<Account>();
			transactions = new ListView<Transaction>();
			
			
			// ========================
			// Setting up the list view
			// ========================
			{
				users.setId("ListView");
				users.setLayoutY(y*primaryStage.getHeight());
				users.setLayoutX(0);
				users.setMaxWidth(w*primaryStage.getWidth());
				users.setMaxHeight(primaryStage.getHeight()*(1-consoleHeight));
				users.setStyle("-fx-background-color: #121212;");
				//specifying the cell design
				users.setCellFactory(new Callback<ListView<Customer>, ListCell<Customer>>() {
		            @Override
		            public ListCell<Customer> call(ListView<Customer> listView) {
		                return new ReprUserListCell();
		            }
		        });
				// adding an onclick event
				users.setOnMouseClicked(new EventHandler<MouseEvent>() {
	
			        @Override
			        public void handle(MouseEvent event) {changeUser();}
			    });
				
				users.setStyle("-fx-background-color: transparent;");
			}
			
			// ==============================
			// Settings for the Accounts list
			// ==============================
			{
				accounts.setId("ListView");
				accounts.setLayoutY(y*primaryStage.getHeight());
				accounts.setLayoutX(w*primaryStage.getWidth());
				accounts.setMaxWidth(w*primaryStage.getWidth());
				accounts.setMaxHeight(primaryStage.getHeight()*(1-consoleHeight)-y);
				accounts.setStyle("-fx-background-color: #121212;");
				//specifying the cell design
				accounts.setCellFactory(new Callback<ListView<Account>, ListCell<Account>>() {
		            @Override
		            public ListCell<Account> call(ListView<Account> listView) {
		                return new ReprAccountListCell();
		            }
		        });
				// adding an onclick event
				accounts.setOnMouseClicked(new EventHandler<MouseEvent>() {
	
			        @Override
			        public void handle(MouseEvent event) {
			        	changeAccount();
			        }
			    });
			}
			
			// ==================================
			// Settings for the Transactions list
			// ==================================
			{
				transactions.setId("ListView");
				transactions.setLayoutY(y*primaryStage.getHeight());
				transactions.setLayoutX(2*w*primaryStage.getWidth());
				transactions.setMaxWidth(w*primaryStage.getWidth());
				transactions.setMinWidth(w*.6*primaryStage.getWidth());
				transactions.setMaxHeight(primaryStage.getHeight()*(1-consoleHeight)-y);
				transactions.setStyle("-fx-background-color: #121212;");
				//specifying the cell design
				transactions.setCellFactory(new Callback<ListView<Transaction>, ListCell<Transaction>>() {
		            @Override
		            public ListCell<Transaction> call(ListView<Transaction> listView) {
		                return new ReprTransactionListCell();
		            }
		        });
			}
			
			updateLists(-1);
			
			
			
			
			
			
			
			
			

			//====================================================================================
			//=========================== Console view settings ==================================
			//====================================================================================
			console = new TextArea();
			console.setLayoutX(0);
			console.setStyle("-fx-text-fill: #bb86fc;");
			console.setLayoutY(primaryStage.getHeight()*(1-consoleHeight)+y);
			console.minWidth(root.getWidth());
			console.minHeight(-primaryStage.getHeight()*(consoleHeight));
			console.setDisable(true);
			console.setStyle("-fx-opacity:90%");
			println("Hello and welcome, this is the app. To interact with it use the menu buttons up top!\nWhen you have added one or more users you can click on them!");
			
			// ================================
			// Adding elements to the root view
			// ================================
			root.getChildren().addAll(users,accounts,transactions,console);
			
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		
		
		
		
	}
	
	
	


	//====================================================================================
	//=========================== Pop up window functions ================================
	//====================================================================================
	
	/** Function responsible for selecting where to save the transaction */
	private void ExportTransaction(Stage primaryStage) {
		if(transactions.getSelectionModel().getSelectedIndex()>=0) {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
			fileChooser.setTitle("What file do you want to save to?");
	        File file = fileChooser.showSaveDialog(primaryStage);
	        if(file.length()!=0) 
	        	modalAssert(primaryStage,file,2);
			else{
	        	try {
					FileWriter fw = new FileWriter(file);
					fw.write(transactions.getSelectionModel().getSelectedItem().toString());
		        	fw.close();
				} catch (IOException e) {
					println("Error : Internal error");
				}
			}
		}
		else
			println("No transaction selected");
	}
	/** Function that is responsible for selecting the write file */
	private void SaveFile(Stage primaryStage) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
		fileChooser.setTitle("What file do you want to save to?");
        File file = fileChooser.showSaveDialog(primaryStage);
        if(file.length()!=0) {
        	modalAssert(primaryStage,file,1);
        	return;
        }
        try {
			IOstream.write(logic, file);
	        updateLists(0);
		} catch (ClassNotFoundException | IOException e) {
			println("Error : Could not find the selected file");
		}
	}
	/** selects the file from wich the user wants to load */
	private void LoadFile(Stage primaryStage) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
		fileChooser.setTitle("What file do you want to load from?");
        File file = fileChooser.showOpenDialog(primaryStage);
        
        try {
			IOstream.read(logic, file);
	        updateLists(0);
		} catch (ClassNotFoundException | IOException e) {
			println(" Error : Read error, file might be corrupt\n");
		}
        
		
	}
	
	/** Popup that appears if the file selected is not empty*/
	private void modalAssert(Stage primaryStage,File file,int Case) {
		final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        
        final Text message = new Text();
        final Button Save = new Button("Confirm");
        final Button Abort = new Button("Don't write to the file");
        

        message.setText(String.format("Please confirm that you want to overwrite the selected file"));
        
        
        // -----------------------------------------------------
        // ---------------- Event Handelers --------------------
        // -----------------------------------------------------
        Save.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	
				try {
					switch(Case) {
					case 1:
						IOstream.write(logic, file);
						break;
					case 2:
						FileWriter fw = new FileWriter(file);
			        	fw.write(transactions.getSelectionModel().getSelectedItem().toString());
			        	fw.close();
						break;
					default:
						break;
					}
					
			        updateLists(0);
			        dialog.close();
				} catch (ClassNotFoundException | IOException E) {
					println("Error : Could not find the selected file");
					dialog.close();
				}
		    }
		});
        Abort.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		        dialog.close();
		    }
		});
        HBox buttonbox = new HBox(Save,Abort);
        VBox dialogVbox = new VBox(message,buttonbox);
        dialogVbox.setLayoutX(dialogVbox.getWidth());
        dialogVbox.setLayoutY(dialogVbox.getHeight());
        Scene dialogScene = new Scene(dialogVbox, 300,150);
        dialog.initStyle(StageStyle.UTILITY);
        dialogVbox.setId("Vbox");
        dialog.setScene(dialogScene);
        dialog.setResizable(false);
        dialog.show();
	}




	/** Pop up where the user can specify wether they want to save before exiting*/
	private void Exit(Stage primaryStage) {
		final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        final Button Exit = new Button("Exit without saving");
        final Button Save = new Button("Save and Exit");
        final Button Abort = new Button("Do not exit");
        

        // -----------------------------------------------------
        // ---------------- Event Handelers --------------------
        // -----------------------------------------------------
        
        Exit.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
				Platform.exit();
		    }
		});
        Save.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	SaveFile(primaryStage);
				Platform.exit();
		    }
		});
        Abort.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		        dialog.close();
		    }
		});
        HBox buttonbox = new HBox(Save,Exit,Abort);
        VBox dialogVbox = new VBox(buttonbox);
        dialogVbox.setLayoutX(dialogVbox.getWidth());
        dialogVbox.setLayoutY(dialogVbox.getHeight());
        Scene dialogScene = new Scene(dialogVbox, 300,150);
        dialog.initStyle(StageStyle.UTILITY);
        dialogVbox.setId("Vbox");
        dialog.setScene(dialogScene);
        dialog.setResizable(false);
        dialog.show();
	}


	/** Responsible for instantiating a popup that allows the user to change the name of a selected customer */
	private void ChangeName(Stage primaryStage) {
		try {
			final Stage dialog = new Stage();
	        dialog.initModality(Modality.APPLICATION_MODAL);
	        dialog.initOwner(primaryStage);
	        
		    // Initiating the local elements
		    TextField firstName = new TextField();
		    TextField lastName = new TextField();
	        final Button confirm = new Button("Confirm");
	        final Button abort = new Button("Cancle");
	        
	        
	        
	        // Setting the text fields text properties
		    Text firstNamePrompt = new Text("What should the new first name be?");
		    Text lastNamePrompt = new Text("What's your last name?");
		    Text socialsecPrompt = new Text("The social security number of the account is:\n"+users.getSelectionModel().getSelectedItem().getSocialSecurity());

	        // -----------------------------------------------------
	        // ---------------- Event Handelers --------------------
	        // -----------------------------------------------------
		    // Adding an onclick event for the confirm button
	        confirm.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			    	//Uses the bank logic class to update the customers name
			    	if(validName(firstName.getText()) && validName(lastName.getText()))
						if(logic.changeCustomerName(firstName.getText(), lastName.getText(), users.getSelectionModel().getSelectedItem().getSocialSecurity()))
							println("Success!");
					else
						println("Error : Could not change the name of that customer\nMake sure that the names don't contain anny non litterals\n");
					println("hmmm");
					updateLists(0);
			        dialog.close();
			    }
			});
	        // Adding an onlick event for the abort button
	        abort.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			        dialog.close();
			    }
			});
	        
	       // Adding the elements to the scene
	        HBox buttonbox = new HBox(confirm,abort);
	        Text Title = new Text();
	        Title.setText("What would you like your new name to be?");
	        VBox dialogVbox = new VBox(Title,firstNamePrompt,firstName,lastNamePrompt,lastName,socialsecPrompt,buttonbox);
	        Scene dialogScene = new Scene(dialogVbox, 600,200);
	        dialog.initStyle(StageStyle.UTILITY);
	        dialogVbox.setId("Vbox");
	        dialog.setScene(dialogScene);
	        dialog.setResizable(false);
	        dialog.show();
			
		}
		catch (Exception e) {
			println(e.getMessage());
		}
	}
	
	
	
	/** responsible for making a popup that asks the user to confirm deletion of the given user*/
	private void removeUser(Stage primaryStage) {
		try {
		// Asserting that a user has been selected
		if(users.getSelectionModel().getSelectedItem()==null)
			return;
		//Initiating the window
		final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);        
        
        // Initiating the local variables
		Customer user = users.getSelectionModel().getSelectedItem();
        final Text message = new Text();
        final Button confirm = new Button("Confirm");
        final Button abort = new Button("Cancle");
        
        // Setting local variable values
        message.setText(String.format("Please confirm that you want to delete the user : %s\n%s \n", user.getName(),user.getSocialSecurity()));

        // -----------------------------------------------------
        // ---------------- Event Handelers --------------------
        // -----------------------------------------------------
        
        // Adding onclick function to abort and confirm
        confirm.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	// Uses the bank logic to remove a user
		    	try {
					console.appendText(String.join(", ",logic.deleteCustomer(user.getSocialSecurity())+"\n"));
					if(logic.Customers.size()==0) {
						AccountMenu.setVisible(false);
						TransactionMenu.setVisible(false);
					}
		    	}
		    	catch(Exception E){
		    		println("Error : Could not delete the selected user,\nmake sure that you have selected a user\n");
		    	}
				updateLists(0);
				dialog.close();
		    }
		});
        abort.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		        dialog.close();
		    }
		});

        // Adding the ui elements to the window
        HBox buttonbox = new HBox(confirm,abort);
        VBox dialogVbox = new VBox(message,buttonbox);
        Scene dialogScene = new Scene(dialogVbox, 350,150);
        dialog.initStyle(StageStyle.UTILITY);
        dialogScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        dialog.setScene(dialogScene);
        dialog.setResizable(false);
        dialog.show();
		
	}
	catch (Exception e) {
		println(e.getMessage());
	}
	}
	/** responsible for creating a popup to assert that the user wants to delete the given account */
	private void removeAccount(Stage primaryStage) {
		try {
			//Asserting that an account has been selected
		if(accounts.getSelectionModel().getSelectedItem()==null)
			return;
		//getting the selected account
		Account acc = accounts.getSelectionModel().getSelectedItem();
		//Initiating the window
		final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        
        //Declaring local variables
        final Text message = new Text();
        final Button confirm = new Button("Confirm");
        final Button abort = new Button("Cancle");
        
        // setting local variable properties
        message.setText(String.format("Please confirm that you want to delete the account :\nAccount number : %d\nbalance : %.3f", acc.getAccNum(),acc.getAccountBalance()));

        // -----------------------------------------------------
        // ---------------- Event Handelers --------------------
        // -----------------------------------------------------
        
        // Adding onclick events
        confirm.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	// Using the bank logic to remove the account
		    	try {
		    		println(logic.closeAccount(users.getSelectionModel().getSelectedItem().getSocialSecurity(), acc.accountNumber));
		    	}
		    	catch(Exception E) {
		    		println("Error : could not remove the selected account, \nmake sure that you have selected an account\n");
		    	}
		    		updateLists(1);
				dialog.close();
		    }
		});
        abort.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		        dialog.close();
		    }
		});


        // Adding the ui elements to the window
        HBox buttonbox = new HBox(confirm,abort);
        VBox dialogVbox = new VBox(message,buttonbox);
        Scene dialogScene = new Scene(dialogVbox, 350,150);
        dialog.initStyle(StageStyle.UTILITY);
        dialogScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        dialog.setScene(dialogScene);
        dialog.setResizable(false);
        dialog.show();
		
	}
	catch (Exception e) {
		println(e.getMessage());
	}
	}
	
	
	
	
	
	/** Creating a popup window where the user can enter the info for a new account */
	private void addAccount(Stage primaryStage) {
		try {
			//Asserting that a user has been selected
			if(users.getSelectionModel().getSelectedItem()==null)
				return;
			
			//Initiating the window
			final Stage dialog = new Stage();
	        dialog.initModality(Modality.APPLICATION_MODAL);
	        dialog.initOwner(primaryStage);
	
		    // Defining local variables
	        ObservableList<String> options = 
	        	    FXCollections.observableArrayList(
	        	        "Savings account",
	        	        "Credit account"
	        	    );
	        final ComboBox<String> menuButton = new ComboBox<String>(options);
	        final Button confirm = new Button("Confirm");
	        final Button abort = new Button("Cancle");
	        

	        // -----------------------------------------------------
	        // ---------------- Event Handelers --------------------
	        // -----------------------------------------------------
	        
	        //Adding on click evennts for the buttons
	        confirm.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			    	//Creating a new account using the bank logic
			    	try {
				    	switch(menuButton.getSelectionModel().getSelectedIndex()) {
					    	case 0:
					    		System.out.println(menuButton.getSelectionModel().getSelectedItem());
					    		console.appendText(Integer.toString(logic.createSavingsAccount(users.getSelectionModel().getSelectedItem().getSocialSecurity()))+"\n");
					    		break;
					    	case 1:
					    		System.out.println(menuButton.getSelectionModel().getSelectedItem());
					    		console.appendText(Integer.toString(logic.createCreditAccount(users.getSelectionModel().getSelectedItem().getSocialSecurity()))+"\n");
					    		break;
				    	}
			    	}
			    	catch(Exception E) {
			    		println("Error : Internal error please try again\n");
			    	}
			    	updateLists(1);
			        dialog.close();
			    }
			});
	        abort.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			        dialog.close();
			    }
			});
	

	        // Adding the ui elements to the window
	        HBox buttonbox = new HBox(confirm,abort);
	        Text Title = new Text();
	        Title.setText("Select an account type?");
	        VBox dialogVbox = new VBox(Title,menuButton,buttonbox);
	        Scene dialogScene = new Scene(dialogVbox, 200,150);
	        dialog.initStyle(StageStyle.UTILITY);
	        dialogScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	        dialog.setScene(dialogScene);
	        dialog.setResizable(false);
	        dialog.show();
	    	
		
		}
		catch (Exception e) {
			println(e.getMessage());
		}
	}
	
	
	/** Responsible for creating a popup where the user can enter the details for a new customer */
	private void addUser(Stage primaryStage) {
		try {
			// Initiating the window
			final Stage dialog = new Stage();
	        dialog.initModality(Modality.APPLICATION_MODAL);
	        dialog.initOwner(primaryStage);
	
		    //Defining local variables
		    TextField firstName = new TextField();
		    TextField lastName = new TextField();
		    Text firstNamePrompt;
		    TextField socialsec = new TextField();
	        final Button confirm = new Button("Confirm");
	        final Button abort = new Button("Cancle");
		    
		    //Setting the values of the local variables
		    firstNamePrompt = new Text("What's your first name?");
		    firstName.setPromptText("What's your first name?");
		    Text lastNamePrompt = new Text("What's your last name?");
		    firstName.setPromptText("What's your last name?");
		    Text socialsecPrompt = new Text("What's your social security number?");
		    socialsec.setPromptText("YYMMDD-XXXX");

	        // -----------------------------------------------------
	        // ---------------- Event Handelers --------------------
	        // -----------------------------------------------------
		    
		    // Adding on click events
	        confirm.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			    	//Adding a new user using the bank logic
			    	if(validName(firstName.getText()) && validName(lastName.getText()) && ValidPnm(socialsec.getText())) {
						if(logic.createCustomer(firstName.getText(), lastName.getText(), socialsec.getText()))
							println("Success!");
			    	}
					else
						println("Error : Please Check that you entered names\nwithout spaces and a valid socialsecurity number\nSocial security numbers should follow the format \"yymmdd-xxxx\"\nand need to be a valid swedish social security number");
					updateLists(0);
			        dialog.close();
			    }
			});
	        abort.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			        dialog.close();
			    }
			});
	
	        // Adding the ui elements to the window
	        HBox buttonbox = new HBox(confirm,abort);
	        Text Title = new Text();
	        Title.setText("What are the user details?");
	        VBox dialogVbox = new VBox(Title,firstNamePrompt,firstName,lastNamePrompt,lastName,socialsecPrompt,socialsec,buttonbox);
	        Scene dialogScene = new Scene(dialogVbox, 200,200);
	        dialog.initStyle(StageStyle.UTILITY);
	        dialogScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	        dialog.setScene(dialogScene);
	        dialog.setResizable(false);
	        dialog.show();
		
		}
		catch (Exception e) {
			println(e.getMessage());
		}
		
	}

	/** Responsible for creating a popup where the user can enter how much they wish to withdraw*/
	private void withdrawPop(Stage primaryStage) {
		try {
			//Initiating the popup
			final Stage dialog = new Stage();
	        dialog.initModality(Modality.APPLICATION_MODAL);
	
		    // Defining and assigning local variables
		    TextField ammount = new TextField();
		    ammount.setPromptText("How much do you want to withdraw");
	        dialog.initOwner(primaryStage);
	        final Button confirm = new Button("Confirm");
	        final Button abort = new Button("Cancle");
	        

	        // -----------------------------------------------------
	        // ---------------- Event Handelers --------------------
	        // -----------------------------------------------------
	        
	        // Adding onlclick functions
	        confirm.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			    	try {
			    	String temp = "";
			    	for(char c : ammount.getText().toCharArray())
			    		if(Character.isDigit(c) || c == '.' || c == ',')
			    			temp+= c;
			    		else
			    		{
			    			println("Error : Not a valid input, Input needs to be a valid\nFloating point number\n");
			    			dialog.close();
			    			return;
			    		}
			        withdrawAmmount(Double.parseDouble(temp));
			        updateLists(3);
			        dialog.close();
				    } catch(Exception E) {
				    	println("Error : Internal error");
				    }
			    }});
	        abort.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			        dialog.close();
			    }
			});
	
	        // Adding the UI elements to the window
	        HBox buttonbox = new HBox(confirm,abort);
	        Text Title = new Text();
	        Title.setText("How much do you want to withdraw?");
	        VBox dialogVbox = new VBox(Title,ammount,buttonbox);
	        Scene dialogScene = new Scene(dialogVbox, 200,150);
	        dialog.initStyle(StageStyle.UTILITY);
	        dialogScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	        dialog.setScene(dialogScene);
	        dialog.setResizable(false);
	        dialog.show();
		
		}
		catch (Exception e) {
			println(e.getMessage());
		}
	}
	
	/** Responsible for creating a popup where the user can enter how much they wich to withdraw */
	private void depositPop(Stage primaryStage) {
		try {
			
			// Initiating the window
			final Stage dialog = new Stage();
	        dialog.initModality(Modality.APPLICATION_MODAL);
	
	        // Defining and assigning local variables
		    TextField ammount = new TextField();
	        dialog.initOwner(primaryStage);
	
	        Text Title = new Text();
	        Title.setText("How much do you want to deposit?");
	        final Button confirm = new Button("Confirm");
	        final Button abort = new Button("Cancle");

	        // -----------------------------------------------------
	        // ---------------- Event Handelers --------------------
	        // -----------------------------------------------------
	        // Adding onlcick functions
	        confirm.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			    	String temp = "";
			    	try {
			    	for(char c : ammount.getText().toCharArray())
			    		if(Character.isDigit(c) || c == '.' || c == ',')
			    			temp+= c;
			    		else
				    		{
				    			println("Error : Not a valid input, Amount needs to be a valid\nfloating point number");
				    			dialog.close();
				    			return;
				    		}
			    	
			        depositAmmount(Double.parseDouble(temp));
			        updateLists(3);
			        dialog.close();
			    	} catch(Exception E) {
				    	println("Error : Internal error");
				    }
			    }
			});
	        abort.setOnAction(new EventHandler<ActionEvent>() {
			    @Override public void handle(ActionEvent e) {
			        dialog.close();
			    }
			});
	
	        // adding the UI elements to the window
	        HBox buttonbox = new HBox(confirm,abort);
	        VBox dialogVbox = new VBox(Title,ammount,buttonbox);
	        dialogVbox.setLayoutX(dialogVbox.getWidth());
	        dialogVbox.setLayoutY(dialogVbox.getHeight());
	        Scene dialogScene = new Scene(dialogVbox, 200,150);
	        dialog.initStyle(StageStyle.UTILITY);
	        dialogScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	        dialog.setScene(dialogScene);
	        dialog.setResizable(false);
	        dialog.show();
		
		}
		catch (Exception e) {
			println(e.getMessage());
		}
	}
	
	
	//====================================================================================
	//======================== Non popup window functions ================================
	//====================================================================================
	/** Responsible for depositing an ammount d from the selected account */
	private void depositAmmount(double d) {
		if(logic.deposit(users.getSelectionModel().getSelectedItem().getSocialSecurity(), accounts.getSelectionModel().getSelectedItem().getAccNum(), d))
			println("Success!");
		else
			println("Could not withdraw the money, check your balance");
		System.out.println(accounts.getSelectionModel().getSelectedItem().toString());
		updateLists(2);
	}
	
	
	/** Responsible for withdrawing an ammount d from the selected account */
	private void withdrawAmmount(double amount) {
		if(logic.withdraw(users.getSelectionModel().getSelectedItem().getSocialSecurity(), accounts.getSelectionModel().getSelectedItem().getAccNum(), amount)) {
			println("Withdrew : "+Double.toString(amount) +" kr");
		}
		else
			println("Error : couldn't withdraw the money\n");
		updateLists(2);
	}
	
	
	/** onclick for the account list, changes the selected account*/
	private void changeAccount() {
		//changing the transaction view
		if(users.getSelectionModel().getSelectedItem()!= null && accounts.getSelectionModel().getSelectedItem() != null ) {
			transactions.setItems(logic.getTransactions(users.getSelectionModel().getSelectedItem().getSocialSecurity()
					, accounts.getSelectionModel().getSelectedItem().getAccNum()));
			println(logic.getAccount(users.getSelectionModel().getSelectedItem().getSocialSecurity(),
					accounts.getSelectionModel().getSelectedItem().getAccNum()));
		}
		updateLists(2);
	}
	
	/** onclick for the user list, changes the selected user */
	private void changeUser() {
		// Changing the accounts view to represent the selected user
		updateLists(1);
		println(String.join(", ", logic.getCustomer(users.getSelectionModel().getSelectedItem().getSocialSecurity())));
	}
	
	/** Writes a line to the UI console window */
	private void println(String str) {
		this.console.appendText(str+"\n");
	}
	
	/** Checks that the social security number is valid */
	boolean ValidPnm(String SocialSecurity) {
		int counter = 0;
		int ret = 0;
		char last = ' ';
		if(SocialSecurity.length()!= 11)
			return false;
		for(char c : SocialSecurity.toCharArray()) {
			if(Character.isDigit(c)) {
				if(counter == SocialSecurity.length()-2)
				{
					last = c;
					break;
				}
				int adder = 2*Integer.parseInt(""+c);
				adder  = adder>=10?adder/10+adder-10:adder; 
				ret+= counter%2 == 0 || counter == 0? adder:Integer.parseInt(""+c);
				counter++;
				
			}
			else if( c != '-' && c!= ' ')
				return false;
			
		}
		int LastNum = (int)(ret/10)*10 ==ret?0:(-ret+(int)(1+ret/10.0) * 10);
		if(Integer.parseInt(Character.toString(last)) == LastNum)
			return true;
		return false;
	}
	
	/** Checks that A name does not contain anny space seperators*/
	boolean validName(String Name) {
		return !Name.contains(" ");
	}
	/** Checks if the withdrawal ammount is a valid floating point */
	boolean validAmmount(String Ammount) {
		try {
		     Float.parseFloat(Ammount);
		     return true;
		}
		catch (NumberFormatException ex) {
		     return false;
		}
	}
	
	/** Updates the list views to make sure that the new data is present */
	void updateLists(int Case) {
		try {
			// case -1 is the first case, should only be ran at the start of the program
			switch(Case) {
			case -1:
				this.users.setItems(this.logic.getCustomers());
				this.accounts.setItems(null);
				this.transactions.setItems(null);
				this.AccountMenu.setVisible(false);
				this.TransactionMenu.setVisible(false);
				break;
			// Case 0 should be ran when adding a new user
			case 0:
				this.users.setItems(null);
				this.users.setItems(logic.getCustomers()!=null?logic.getCustomers():null);
				this.accounts.setItems(null);
				this.transactions.setItems(null);
				this.AccountMenu.setVisible(false);
				this.TransactionMenu.setVisible(false);
				break;
			// Case 1 should be used when selecting a new user
			case 1:
				if(users.getSelectionModel().getSelectedItem().accounts.size()==0)
					this.TransactionMenu.setVisible(false);
				else
					this.TransactionMenu.setVisible(true);
					
				this.accounts.setItems(this.logic.getAccounts(this.users.getSelectionModel().getSelectedItem().getSocialSecurity()));
				this.users.setItems(logic.getCustomers());
				this.AccountMenu.setVisible(true);
				this.transactions.setItems(null);
				break;
			// Should be ran when selecting a new account
			case 2:
				if(users.getSelectionModel().getSelectedItem()!= null && accounts.getSelectionModel().getSelectedItem().getTansactionList().size()!= 0) {
					this.transactions.setItems(this.logic.getTransactions(this.users.getSelectionModel().getSelectedItem().getSocialSecurity(),
							this.accounts.getSelectionModel().getSelectedItem().getAccNum()));
				}
				this.accounts.setItems(logic.getAccounts(users.getSelectionModel().getSelectedItem().getSocialSecurity()));
				this.users.setItems(logic.getCustomers());
				this.AccountMenu.setVisible(true);
				this.TransactionMenu.setVisible(true);
				break;
			// Should be ran when using the transactions
			case 3:
				Customer tempUser = users.getSelectionModel().getSelectedItem();
				Account tempAccount = accounts.getSelectionModel().getSelectedItem();
				this.accounts.setItems(null);
				this.accounts.setItems(logic.getAccounts(users.getSelectionModel().getSelectedItem().getSocialSecurity()));
				if(tempUser!= null && tempAccount.getTansactionList().size()!= 0) {
					this.transactions.setItems(this.logic.getTransactions(tempUser.getSocialSecurity(),
							tempAccount.getAccNum()));
				}this.users.setItems(logic.getCustomers());
				this.AccountMenu.setVisible(true);
				this.TransactionMenu.setVisible(true);
				break;
			}
		}
		catch(Exception E){
			println("Error : Internal error please try again\n");
		}
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
