package seedu.address.ui;

import java.util.List;

import javafx.collections.ObservableList;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import seedu.address.logic.UiLogicHelper;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.logic.util.Action;
import seedu.address.logic.util.ModeEnum;


/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final CommandExecutor commandExecutor;
    private UiLogicHelper uiLogicHelper;

    @FXML
    private TextField commandTextField;

    @FXML
    private Label modeHeader;


    @FXML
    private Label modeLabel;


    @FXML
    private Label modesLabel;

    @FXML
    private MenuBar commandMenuField;

    private Menu temp;
    private List<Menu> currentMenus;
    private List<String> currentCombos;

    public CommandBox(CommandExecutor commandExecutor, UiLogicHelper uiLogicHelper) {
        super(FXML);
        this.commandExecutor = commandExecutor;
        this.uiLogicHelper = uiLogicHelper;

        modeHeader.getStyleClass().add("label-bright");
        modeLabel.getStyleClass().add("label-bright");

        initialiseText();
        fillMenu();
        fillCombo();

    }

    /**
     *  Sets change detection callback to textfield
     */
    private void initialiseText() {
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());

        commandTextField.textProperty().addListener((observable, oldCommand, newCommand) -> {
            //System.out.println("command changing from " + oldCommand + " to " + newCommand);
            updateMenu(oldCommand, newCommand);
        });
    }

    /**
     * updates menu based on changes detected
     * @param oldCommand in text field
     * @param newCommand in text field
     */
    private void updateMenu(String oldCommand, String newCommand) {
        commandMenuField.getMenus().clear();
        for (Action temp : uiLogicHelper.getMenuItems(newCommand)) {
            Label label = new Label(temp.toString());
            label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    commandTextField.setText(temp.toString() + " ");
                    commandTextField.positionCaret(temp.toString().length() + 1);
                }
            });
            Menu button = new Menu();
            button.setGraphic(label);
            commandMenuField.getMenus().add(button);
        }
    }

    /**
     *  Fills in Menu bar in UI
     */
    private void fillMenu() {
        for (Action temp : uiLogicHelper.getMenuItems("")) {
            Label label = new Label(temp.toString());
            label.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    commandTextField.setText(temp.toString() + " ");
                    commandTextField.positionCaret(temp.toString().length() + 1);
                }
            });
            Menu button = new Menu();
            button.setGraphic(label);
            commandMenuField.getMenus().add(button);
        }
    }

    /**
     *  Fills in combo in ui
     */
    private void fillCombo() {
        modeLabel.setText(uiLogicHelper.getMode().toString());
        String modes = " | ";
        List<ModeEnum> temp = uiLogicHelper.getModes();

        for (ModeEnum mode : temp) {
            if (!uiLogicHelper.getMode().toString().equals(mode.toString())) {
                modes += (mode.toString() + " | ");
            }
        }

        if (modes.equals(" | ")) {
            modes = "None";
        }
        modesLabel.setText(modes);
    }


    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        try {
            commandExecutor.execute(commandTextField.getText());
        } catch (CommandException | ParseException e) {
            setStyleToIndicateCommandFailure();
        }
        fillCombo();
        //commandComboField.setValue(uiLogicHelper.getMode().toString());
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

    /**
     * Represents a function that can execute commands.
     */
    @FunctionalInterface
    public interface CommandExecutor {
        /**
         * Executes the command and returns the result.
         *
         * @see seedu.address.logic.Logic#execute(String)
         */
        CommandResult execute(String commandText) throws CommandException, ParseException;
    }

    void clearCommandBox() {
        commandTextField.setText("");
    }

    void setGuessTextAndCaret() {
        commandTextField.setText("guess ");
        commandTextField.positionCaret(6);
    }

}
