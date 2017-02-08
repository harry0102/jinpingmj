package com.test.pack;

import com.test.mahjong.gy.core.GameRoom;
import com.test.mahjong.gy.core.Room.Config;

public class Test {
	public static void main(String[] args) {
		Config config = new Config(1);
		config.setWanFa(4);
		GameRoom gr = new GameRoom(config);		
		gr.startGame();
	}
}
