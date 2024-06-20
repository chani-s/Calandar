import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.util.*;

public class ProjectController {

    @FXML
    private Button add;

    @FXML
    private Button delete;

    @FXML
    private Text currentMonth;

    @FXML
    private GridPane gridPane;

    @FXML
    private ListView<Label> meetingsList;

    @FXML
    private ComboBox<Integer> monthsBox;

    @FXML
    private TextField newMeeting;

    @FXML
    private ComboBox<Integer> yearsBox;

    @FXML
    private Text numOfMonth;

    @FXML
    private Text titleForMeetings;

    final private String [] monthsNames = {"JANUARY","FEBRUARY","MARCH","APRIL","MAY","JUNE","JULY","AUGUST","SEPTEMBER","OCTOBER","NOVEMBER","DECEMBER"};
    final private int numOfRows = 7, numOfCols = 7;
    private Button [][] days = new Button[numOfRows][numOfCols];
    private Calendar calendar;
    private HashMap<Calendar, ArrayList<Label>> meetings;

    public void initialize()
    {
        monthsBox.getItems().addAll(1,2,3,4,5,6,7,8,9,10,11,12);
        for (int i = 2020; i < 2100; i++)
        {
            yearsBox.getItems().add(i);
        }
        for(Node n : gridPane.getChildren()) {
            if(GridPane.getColumnIndex(n) != null && GridPane.getRowIndex(n) != null && n instanceof Button)
                days[GridPane.getRowIndex(n)][GridPane.getColumnIndex(n)] = ((Button) n);
        }
        calendar = Calendar.getInstance();
        currentMonth.setText(""+ calendar.get(Calendar.YEAR) + " " + monthsNames[calendar.get(Calendar.MONTH)]);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
        setCalendar(calendar);
        meetings = new HashMap<>();
    }

    @FXML
    void showChosenMonth(ActionEvent event)
    {
        if(monthsBox.getValue() == null || yearsBox.getValue() == null)
            return;
        calendar.set(yearsBox.getValue(),monthsBox.getValue()-1, 1);
        setCalendar(calendar);
    }

    private void setCalendar(Calendar c)
    {
        int numOfDay = 1, first = c.get(Calendar.DAY_OF_WEEK)-1;
        for (int i = 1; i < numOfRows; i++)
        {
            for (int j = 0; j < numOfCols; j++) {
                while (i == 1 && j < first || (numOfDay > c.getActualMaximum(Calendar.DATE) && j < numOfCols)) {
                    days[i][j].setText("");
                    days[i][j].setVisible(false);
                    j++;
                }
                if(j < numOfCols)
                {
                    days[i][j].setText("" + numOfDay);
                    days[i][j].setVisible(true);
                    numOfDay++;
                }
            }
        }
        changeElements();
        //delete.setVisible(false);
    }

    private void changeElements()
    {
        for (int i = 1; i < numOfRows; i++) {
            for (int j = 0; j < numOfCols; j++) {
                days[i][j].setStyle("-fx-background-color:  #8f6139;");
            }
        }
        add.setDisable(true);
        if(calendar.get(Calendar.MONTH)<9)
            numOfMonth.setText("0"+(calendar.get(Calendar.MONTH)+1));
        else
            numOfMonth.setText(""+(calendar.get(Calendar.MONTH)+1));
        currentMonth.setText(calendar.get(Calendar.YEAR) + "\n" + monthsNames[calendar.get(Calendar.MONTH)]);
        titleForMeetings.setText("Select a day to view");
    }

    @FXML
    void onChoosingDate(ActionEvent event)
    {
        for (int i = 1; i < numOfRows; i++) {
            for (int j = 0; j < numOfCols; j++) {
                days[i][j].setStyle("-fx-background-color:  #8f6139;");
            }
        }
        ((Button)event.getSource()).setStyle("-fx-font-size: 17;");
        meetingsList.getItems().clear();
        add.setDisable(false);
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), Integer.parseInt (((Button)event.getSource()).getText()));
        titleForMeetings.setText("Your meetings in "+calendar.get(Calendar.DAY_OF_MONTH)+"."+(calendar.get(Calendar.MONTH)+1)+"."+calendar.get(Calendar.YEAR)+":");
        if(meetings.get(calendar) != null)
            meetingsList.getItems().addAll(meetings.get(calendar));
        else
            meetings.put(calendar, new ArrayList<Label>());
    }

    @FXML
    void addMeeting(ActionEvent event) {
        Label l1 = new Label(newMeeting.getText());
        if (l1.getText().equals("")) {
            if (add.getText().equals("change"))
                add.setText("add");
            return;
        }
        meetings.get(calendar).add(l1);
        meetingsList.getItems().add(l1);
        newMeeting.clear();
        l1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                int index = meetingsList.getSelectionModel().getSelectedIndex();
                newMeeting.setText(((Label) event.getSource()).getText());
                add.setText("change");
                //delete.setVisible(true);
                meetingsList.getItems().remove(index);
                meetings.remove(calendar, ((Label) event.getSource()).getText());
            }
        });
        if(add.getText().equals("change"))
            add.setText("add");
        //delete.setVisible(false);

    }

//    @FXML
//    void deleteMeeting(MouseEvent event) {
//        newMeeting.clear();
//        add.setText("add");
//        delete(event);
//    }
//
//    private void delete(MouseEvent event)
//    {
//        int index = meetingsList.getSelectionModel().getSelectedIndex();
//        newMeeting.setText(((Label) event.getSource()).getText());
//        add.setText("change");
//        delete.setVisible(true);
//        meetingsList.getItems().remove(index);
//        meetings.remove(calendar, ((Label) event.getSource()).getText());
//    }

}

