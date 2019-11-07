package common;

import org.json.JSONObject;

public class Message {
	private int Type;
	private JSONObject json;
	private final String TYPE_ID_NAME = "Type";
	
	public Message(int pType) {
		this.Type = pType;
		json = new JSONObject();
		addField(TYPE_ID_NAME, pType);
	}
	
	public Message(String pJson) {
		try 
		{
			json = new JSONObject(pJson);
			this.Type = json.getInt(TYPE_ID_NAME);
		}
		catch (Exception ex)
		{
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
	}	
	
	public void addField(String pKey, String pValue) {
		try 
		{
			json.put(pKey, pValue);
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void addField(String pKey, int pValue) {
		try 
		{
			json.put(pKey, pValue);
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void addField(String pKey, float pValue) {
		try 
		{
			json.put(pKey, pValue);
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}	
	
	public void addField(String pKey, boolean pValue) {
		try 
		{
			json.put(pKey, pValue);
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	public String getValue(String pKey) {
		String result = "";
		try 
		{
			result = json.get(pKey).toString();
		} 
		catch (Exception ex)
		{
			result = "";
		}
		return result;
	}

	public boolean getBoolValue(String pKey) {
		boolean result = false;
		try 
		{
			result = (boolean)json.get(pKey);
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return result;
	}

	public int getIntValue(String pKey) {
		int result = -1;
		try 
		{
			result = (int)json.get(pKey);
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return result;
	}

	public float getFloatValue(String pKey) {
		float result = 0.0f;
		try 
		{
			result = (float)json.get(pKey);
		} 
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		return result;
	}
	
	public String getJSonString() {
		return json.toString();
	}

	public int getType() {
		return Type;
	}

	public void setType(int type) {
		Type = type;
	}	
}
