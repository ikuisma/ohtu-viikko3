package ohtu;

import com.google.gson.Gson;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.http.client.fluent.Request;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Main {

    public static void main(String[] args) throws IOException {
        // vaihda oma opiskelijanumerosi seuraavaan, ÄLÄ kuitenkaan laita githubiin omaa opiskelijanumeroasi
        String studentNr = "";
        if ( args.length>0) {
            studentNr = args[0];
        }

        String urlSubmission = "https://studies.cs.helsinki.fi/ohtustats/students/"+studentNr+"/submissions";
        String urlCourse = "https://studies.cs.helsinki.fi/ohtustats/courseinfo";
        String urlStats = "https://studies.cs.helsinki.fi/ohtustats/stats";

        String bodyText = Request.Get(urlSubmission).execute().returnContent().asString();
        String courseText = Request.Get(urlCourse).execute().returnContent().asString();
        String statsText = Request.Get(urlStats).execute().returnContent().asString();

        Gson mapper = new Gson();
        Submission[] subs = mapper.fromJson(bodyText, Submission[].class);
        CourseData course = mapper.fromJson(courseText, CourseData.class);

        System.out.println("Kurssi: " + course.getName() + ", " + course.getTerm() + "\n");
        System.out.println("opiskelijanumero: " + studentNr + "\n");

        int i = 0;
        for (Submission submission : subs) {
            int total = course.getExercises().get(i);
            String ex = submission.getExercises().stream().map(e -> e.toString()).collect (Collectors.joining (" "));
            int tehdyt = submission.getExercises().size();
            System.out.println("viikko " + submission.getWeek());
            System.out.print("  tehtyjä tehtäviä yhteensä " + tehdyt + " (maksimi " + total + ")");
            System.out.print(", aikaa kului " + submission.getHours() + " tuntia,");
            System.out.print(" tehdyt tehtävät: " + ex + "\n");
            i++;
        }

        int tehtäviä = Arrays.stream(subs).map(e -> e.getExercises().size()).reduce(0, Integer::sum);
        int tunteja = Arrays.stream(subs).map(e -> e.getHours()).reduce(0, Integer::sum);

        System.out.println();
        System.out.println("Yhteensä: " + tehtäviä + " tehtävää " + tunteja + " tuntia \n");

        JsonParser parser = new JsonParser();
        JsonObject parsedStats = parser.parse(statsText).getAsJsonObject();
        List<JsonObject> stats = parsedStats.entrySet().stream().map(e -> e.getValue().getAsJsonObject()).collect(Collectors.toList());

        int handins = stats.stream().map(e -> e.get("students").getAsInt()).reduce(0, Integer::sum);
        int returnedExercises = stats.stream().map(e -> e.get("exercise_total").getAsInt()).reduce(0, Integer::sum);
        System.out.println("kurssilla yhteensä " + handins + " palautusta, palautettuja tehtäviä " + returnedExercises + " kpl");
    }

}

class CourseData {

    private String name;
    private List<Integer> exercises;
    private String term;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getExercises() {
        return exercises;
    }

    public void setExercises(List<Integer> exercises) {
        this.exercises = exercises;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }
}
