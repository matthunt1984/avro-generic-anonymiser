import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import org.apache.avro.Schema;
import org.apache.avro.Schema.Field;
import org.apache.avro.Schema.Type;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.io.JsonDecoder;

public class main {
  public static void main(String[] args) throws IOException {

    Path resources = Paths.get("src", "main", "resources");
    Path schemaPath = resources.resolve("person.avsc");
    Path jsonMsgPath = resources.resolve("person1.json");

    Schema schema = new Schema.Parser().parse(new File(schemaPath.toString()));

    String jsonMsg = Files.readString(Paths.get(jsonMsgPath.toString()));
    JsonDecoder jsonDecoder = DecoderFactory.get().jsonDecoder(schema,
        jsonMsg);

    DatumReader<GenericRecord> datumReader = new GenericDatumReader<GenericRecord>(schema);
    GenericRecord a = datumReader.read(null, jsonDecoder);

    System.out.println("Input: " + a.toString());

    GenericRecord b;
    //b = GenericData.get().deepCopy(schema, a);
    b = a;

    Anonymise(b);
    System.out.println("Output: " + b.toString());
  }

  private static GenericRecord Anonymise(GenericRecord record) {
    Schema s = record.getSchema();

    if (s.getType() == Type.RECORD) {
      for (Field f : s.getFields()) {
        String ispii = Optional.ofNullable(f.getProp("ispii")).orElse("false");

        if (ispii.equals("true")) {
          record.put(f.name(), "REDACTED");
        }
      }
    }

    return record;
  }
}