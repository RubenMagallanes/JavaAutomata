package main.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JPanel;

import main.Main;
import main.load.JarData;
import main.load.JarLoader;
import main.util.DesktopApi;

public class ButtonPanel extends JPanel{


	public ButtonPanel (){
		JButton button = new JButton("Load Jar");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JarData jarData = JarLoader.loadJarFile(JarFileChooser.chooseJarFile());
				Main.setJarData(jarData);
			}
		});
		this.add(button);

		button = new JButton("Open View");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				File htmlFile = new File("src/web/index.html");
				DesktopApi.browse(htmlFile.toURI());
			}
		});
		this.add(button);
	}
}
