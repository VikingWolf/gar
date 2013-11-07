import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.orion.model.Card;
import com.orion.model.CardSet;
import com.orion.model.Game;
import com.orion.model.Player;


public class Test {

	private	Scanner keyboard;

	private Game game;

	private JPanel[] panels;
	
	private JTextArea logArea;
	
	private JTextArea sitesArea;
	
	private JTextArea notificationArea;
	
	private JLabel actionInfo;
			
	private JTextArea additionalInfoArea;
	
	private JButton actionButton;
	
	private JList actionList;
	
	private DefaultListModel actionListModel = new DefaultListModel();
	
	private JTextArea[] playerAreas;

	private List<Object> action;
	
	private boolean waiting = false;
	
	final static Logger logger = LoggerFactory.getLogger(Test.class);

	public static void main(String[] args) {
		Test test = new Test();
		test.setUp(3);
		test.chooseFirstPlayer();
	}

	public Test() {
		super();
		this.action = new ArrayList<Object>();
	}
	
	/* GUI */
	public void setUpGUI(){
		JFrame mainFrame = new JFrame("GTR Test");
		mainFrame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				logger.error("Closing GTR Test...");
				System.exit(0);				
			}

		});
		mainFrame.setBounds(new Rectangle(100, 100, 820, 600));
		JTabbedPane tabPane = new JTabbedPane();
		tabPane.setBounds(new Rectangle(5, 5, 790, 520));
		String[] panelTitles = {"Acciones", "Registro", "Estado", "Jugadores", };
		panels = new JPanel[panelTitles.length];
		for (int i = 0; i < panelTitles.length; i++){
			panels[i] = new JPanel();
			panels[i].setLayout(null);
			tabPane.addTab(panelTitles[i], panels[i]);
		}
		setUpInputPane();
		setUpLogPane();
		setUpStatusPane();
		setUpPlayersPane();
		notificationArea = new JTextArea();
		notificationArea.setForeground(Color.RED);
		JScrollPane notificationAreaScroll = new JScrollPane(notificationArea);
		notificationAreaScroll.setBounds(new Rectangle(5, 530, 800, 20));		
		mainFrame.getContentPane().setLayout(null);
		mainFrame.getContentPane().add(tabPane);
		mainFrame.getContentPane().add(notificationAreaScroll);
		mainFrame.setVisible(true);
	}
	
	private void setUpInputPane(){
		actionInfo = new JLabel();
		actionInfo.setBounds(new Rectangle(20, 20, 750, 20));
		actionInfo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panels[0].add(actionInfo);				
		actionListModel = new DefaultListModel();
		actionList = new JList(actionListModel);
		actionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		actionList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		JScrollPane actionListScroll = new JScrollPane(actionList);
		actionListScroll.setBounds(new Rectangle(20, 60, 600, 200));
		panels[0].add(actionListScroll);
		actionButton = new JButton("Escoger");
		actionButton.setBounds(new Rectangle(640, 90, 100, 30));
		actionButton.addActionListener(new ChooseActionButtonListener(this));
		panels[0].add(actionButton);
		additionalInfoArea = new JTextArea();
		JScrollPane additionalInfoAreaScroll = new JScrollPane(additionalInfoArea);
		additionalInfoAreaScroll.setBounds(20, 280, 750, 200);
		panels[0].add(additionalInfoAreaScroll);
	}
	
	private void setUpLogPane(){
		logArea = new JTextArea();		
		JScrollPane logAreaScroll = new JScrollPane(logArea);
		logAreaScroll.setBounds(10, 10, 550, 200);
		panels[1].add(logAreaScroll);
	}
	
	private void setUpStatusPane(){
		sitesArea = new JTextArea();		
		JScrollPane sitesAreaScroll = new JScrollPane(sitesArea);
		sitesAreaScroll.setBounds(10, 10, 750, 400);
		panels[2].add(sitesAreaScroll);
	}
	
	private void setUpPlayersPane(){
		JTabbedPane playersTabPane = new JTabbedPane();
		playerAreas	= new JTextArea[game.getPlayers().size()];
		JScrollPane[] playerAreasScroll = new JScrollPane[game.getPlayers().size()];
		JPanel[] playerPanels = new JPanel[game.getPlayers().size()];
		for (int i = 0; i < game.getPlayers().size(); i++){
			playerAreas[i] = new JTextArea();
			playerAreasScroll[i] = new JScrollPane(playerAreas[i]);
			playerAreasScroll[i].setBounds(10, 10, 600, 400);
			playerPanels[i] = new JPanel();
			playerPanels[i].setLayout(null);
			playerPanels[i].add(playerAreasScroll[i]);
			playersTabPane.addTab(game.getPlayers().get(i).getName(), playerPanels[i]);
		}
		playersTabPane.setBounds(new Rectangle(10, 10, 750, 450));
		panels[3].add(playersTabPane);
	}
	
	private void updateInputPane(RequiredAction action, final String additionalInfo){
		this.actionInfo.setText(action.getSubject() + " " + action.getActionInfo());
		this.additionalInfoArea.setText(additionalInfo);
		actionListModel.removeAllElements();
		for (Integer index : action.getActionOptions().keySet()){
			actionListModel.addElement(action.getActionOptions().get(index));
		}
	}
	
	public void clearInputPane(){
		this.actionInfo.setText("Sin novedad en el frente");
		this.additionalInfoArea.setText("");
		actionListModel.removeAllElements();
	}

	
	private void updateStatusPane(){
		sitesArea.setText(game.getSites().toString() + "\n\n" + game.getPool().toString());		
	}
	
	private void updatePlayersPane(){
		for (int i = 0; i < this.playerAreas.length; i++){
			updatePlayerView(i);
		}
	}
	
	private void updatePlayerView(int index){
		playerAreas[index].setText(game.getPlayers().get(index).toString());
	}
	
	private void addLog(String log){
		logArea.append(log);
	}
	
	private void addNotification(RequiredAction action){
		notificationArea.setText(action.getSubject() + ": " + action.getActionInfo()+"\n");		
	}
	
	public Object getSelectedOption(){
		return actionList.getSelectedValue();
	}
	
	/* game related */
	public void setUp(int players){
		 keyboard = new Scanner(System.in);
		 this.game = new Game(players);
		 setUpGUI();
		 updateStatusPane();
		 updatePlayersPane();
		 addLog("Game created for " + players + " players.\nInitial hand assigned. ");
	}
	
	public void chooseFirstPlayer(){
		while (game.notLastPlayer()){
			RequiredAction action = new RequiredAction(game.getCurrentPlayer().getName(), " escoge una carta para decidir el jugador inicial. ", game.playerInitialOptions());
			game.nextPlayer();
			this.addNotification(action);
			this.updateInputPane(action, game.getCurrentPlayer().getHand().toString());
			pause();			
		}
		clearInputPane();
		/**
		this.game.chooseFirstPlayer(0, 0, 0);
		this.game.giveSenators();
		updateStatusPane();
		updatePlayersPane();*/
	}
		
	/* getters and setters */

	public Object getAction() {
		return action;
	}

	public void addAction(Object action) {
		this.action.add(action);
	}
	
	public void clearAction(){
		this.action = new ArrayList<Object>();
	}
	
	public void pause(){
		this.setWaiting(true);
		while (isWaiting()){			
		}
	}
	
	public void resume(){
		setWaiting(false);
	}

	private synchronized boolean isWaiting() {
		return waiting;
	}

	private synchronized void setWaiting(boolean waiting) {
		this.waiting = waiting;
	}
			
}

class RequiredAction {
	private String subject;
	private String actionInfo;
	private Map<Integer, Object> actionOptions;
	
	public RequiredAction(String subject, String actionInfo, CardSet<Card> options) {
		super();
		this.subject = subject;
		this.actionInfo = actionInfo;
		actionOptions = new HashMap<Integer, Object>();
		for (int i = 0; i < options.size(); i++){
			actionOptions.put(options.get(i).getId(), options.get(i));
		}
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getActionInfo() {
		return actionInfo;
	}
	
	public void setActionInfo(String actionInfo) {
		this.actionInfo = actionInfo;
	}
	
	public Map<Integer, Object> getActionOptions() {
		return actionOptions;
	}
	
	public void setActionOptions(Map<Integer, Object> actionOptions) {
		this.actionOptions = actionOptions;
	}
	
}

class ChooseActionButtonListener implements ActionListener{
	
	private Test parent;
	
	public ChooseActionButtonListener(Test parent){
		super();
		this.parent = parent;
	}

	public void actionPerformed(ActionEvent e) {
		Object selection = parent.getSelectedOption();		
		System.out.println(selection);
		if (selection != null){
			parent.addAction(selection);
			parent.resume();
		}
	}
}

