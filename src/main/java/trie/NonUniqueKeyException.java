package trie;

public class NonUniqueKeyException extends Exception
{
	String key;

	public NonUniqueKeyException( String s )
	{
		super( "attempting to insert '" + s + "'" );
		key = s;
	}

	public String getKey()
	{
		return( key );
	}
}
