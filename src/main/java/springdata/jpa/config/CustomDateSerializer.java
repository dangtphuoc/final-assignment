package springdata.jpa.config;

import java.io.IOException;
import java.util.Date;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.util.ISO8601DateFormat;

public class CustomDateSerializer extends JsonSerializer<Date> {

	@Override
	public void serialize(Date value, JsonGenerator jgen,
			SerializerProvider provider) throws IOException,
			JsonProcessingException {
		ISO8601DateFormat ISOformatter = new ISO8601DateFormat();
		String formattedDate = ISOformatter.format(value);

		jgen.writeString(formattedDate);
	}

}
