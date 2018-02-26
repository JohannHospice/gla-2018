package gla2018.grp4;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
public class MyResource
{
	public class Example
	{
		public String example; // See BeanSerializer

		public Example(String str)
		{
			example = str;
		}
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	//@Path("/")
	public Example foo()
	{
		return new Example("foo");
	}
}
