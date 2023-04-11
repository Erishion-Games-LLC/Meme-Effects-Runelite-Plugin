package com.erishiongames.memeeffects.messages;

public enum MessageValues
{

	DRAGON_BATTLEAXE("Raarrrrrgggggghhhhhhh!"),
	DRAGON_PICKAXE("Smashing!"),
	DRAGON_HARPOON("Here fishy fishies!"),
	DRAGON_AXE("Chop chop!"),

	;


	private final String message;

	MessageValues(String message)
	{
		this.message = message;
	}

	public String getMessage()
	{
		return message;
	}
}
