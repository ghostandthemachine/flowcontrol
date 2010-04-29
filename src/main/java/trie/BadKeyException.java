package trie;

public class BadKeyException extends Exception
{
	public BadKeyException( char c )
	{
		super( "converting '" + c + "' (" +
			(new java.lang.Character( c )).toString() + ") to range 0->25" );
	}

	public BadKeyException( String s )
	{
		super( s );
	}
}
