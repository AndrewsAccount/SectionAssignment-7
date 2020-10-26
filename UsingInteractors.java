import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.*;
import javax.swing.*;
import acm.graphics.*;
import acm.program.*;

public class UsingInteractors extends GraphicsProgram {
	private static final int BOX_WIDTH = 120;
	private static final int BOX_HEIGHT = 50;

	private JTextField textBox;
	private GLabel returnBox;
	private JButton addButton;
	private JButton remove;
	private JButton clear;

	private JLabel jlstring;
	private GRect rect;

	GObject moveCompound;
	GPoint point;

	// List<GCompound> listOfCompounds = new ArrayList<>();
	//
	Map<String, GCompound> listOfCompounds;

	private GCompound compound;

	public void init() {
		southToolBar();
		addActionListeners();
		addMouseListeners();
	}

	public void southToolBar() {
		add(new JLabel("Name: "), SOUTH);
		add(textBox = new JTextField(40), SOUTH);
		textBox.addActionListener(this);

		add(addButton = new JButton("Add"), SOUTH);
		add(remove = new JButton("Remove"), SOUTH);
		add(clear = new JButton("Clear"), SOUTH);
	}

	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();

		if (obj == addButton || obj == textBox)
			createReturnBox(textBox.getText());
		if (obj == remove)
			removeBox(textBox.getText());
		if (obj == clear)
			clearAll();

	}

	public void createReturnBox(String name) {
		int x = getWidth() / 2;
		int y = getHeight() / 2;

		rect = new GRect(BOX_WIDTH, BOX_HEIGHT);
		returnBox = new GLabel(name);
		compound = new GCompound();

		compound.add(rect, -BOX_WIDTH / 2, -BOX_HEIGHT / 2);
		compound.add(returnBox, -returnBox.getWidth() / 2, returnBox.getAscent() / 2);

		add(compound, x, y);

		listOfCompounds.put(name, compound);

	}

	public void removeBox(String name) {
//		if(listOfCompounds.containsKey(name)) {
//			remove(compound);
		GObject temp = listOfCompounds.get(name);
		if (temp != null) {
			remove(temp);
		}
	}

	public void clearAll() {
//		for(int i = 0; i < listOfCompounds.size(); i++) {
//			remove(compound);
//		}
//	
		Iterator<String> iterator = listOfCompounds.keySet().iterator();
		while (iterator.hasNext()) {
			removeBox(iterator.next());
		}
		listOfCompounds.clear();
	}

	public void mouseClicked(MouseEvent e) {
		if (moveCompound != null)
			moveCompound.sendToFront();
	}

	public void mousePressed(MouseEvent e) {
		point = new GPoint(e.getPoint());
		moveCompound = (GObject) getElementAt(point);
	}

	public void mouseDragged(MouseEvent e) {
		
		if (moveCompound != null) {
			moveCompound.move(e.getX() - point.getX(), e.getY() - point.getY());
			point = new GPoint(e.getPoint());
		}
	}
}
