package ohtu;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.Arrays;

import org.apache.http.client.fluent.Request;

public class Main {

    public static void main(String[] args) throws IOException {
        // vaihda oma opiskelijanumerosi seuraavaan, ÄLÄ kuitenkaan laita githubiin omaa opiskelijanumeroasi
        String studentNr = "";
        if ( args.length>0) {
            studentNr = args[0];
        }

        String url = "https://studies.cs.helsinki.fi/ohtustats/students/"+studentNr+"/submissions";

        String bodyText = Request.Get(url).execute().returnContent().asString();

        System.out.println("json-muotoinen data:");
        System.out.println( bodyText );

        Gson mapper = new Gson();
        Submission[] subs = mapper.fromJson(bodyText, Submission[].class);
        int tehtäviä = Arrays.stream(subs).map(e -> e.getExercises().size()).reduce(0, Integer::sum);
        int tunteja = Arrays.stream(subs).map(e -> e.getHours()).reduce(0, Integer::sum);

        System.out.println("opiskelijanumero: " + studentNr);
        System.out.println();
        for (Submission submission : subs) {
            System.out.println(submission);
        }
        System.out.println();
        System.out.println("Yhteensä: " + tehtäviä + " tehtävää " + tunteja + " tuntia");
    }
}
