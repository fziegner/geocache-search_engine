package ir.SearchEngine.GeocacheSearchEngine;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.json.JSONArray;
import org.json.JSONObject;

import ir.SearchEngine.GeocacheSearchEngine.Parser.FileIO;

@Path("/search")
public class SearchResource {
	
	/**
	 * execute a search by a given query
	 * @param query String query to search for
	 * @return JSONArray containing the JSONObject of the caches that matched the query
	 */
	@GET
	@Path("/{query}")
	@Produces(MediaType.APPLICATION_JSON)
	public String search(@PathParam("query") String query) {
		System.out.println("you searched for: " + query);
		Searcher searcher;
		TopDocs docs;
		ScoreDoc[] hits; 
		List<String> cacheWaypoints = new ArrayList<String>();
		try {
			//execute the search in the index
			searcher = new Searcher(CONSTANTS.INDEX_DIRECTORY);
			docs = searcher.search(query);
			System.out.println("after seach, found: " + docs.toString());
			hits = docs.scoreDocs;
			System.out.println("number of hits: " + hits.length);
			for(int i = 0; i < hits.length; i++) {
				Document d = searcher.getDocument(hits[i]);
				String waypoint = d.get("waypoint");
				cacheWaypoints.add(waypoint);
				System.out.println("found: " + d.toString());
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (org.apache.lucene.queryparser.classic.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		JSONArray caches = new JSONArray();
		
		//open all found caches and append them to the result
		for(String waypoint : cacheWaypoints) {
			String jsonStr = FileIO.readFile(CONSTANTS.DATA_DIRECTORY + "/" + waypoint + ".json");
			System.out.println("path to found data: " + jsonStr);
			caches.put(new JSONObject(jsonStr));
		}
		
		
		//return caches;
		System.out.println(caches);
		return "{\"hiddenAt\":\"18.11.2002\",\"waypoint\":\"OC00A2\",\"coordinates\":\"E 007° 01.927' N 51° 02.121'\",\"link\":\"https://www.opencaching.de/viewcache.php?wp=OC00A2\",\"description\":\"Der Weg ist durchweg fahrrad- und kinderwagentauglich. Erst aufden allerletzten Metern zur Dose müssen die Wege verlassenwerden.1.Suche bei den oben angegebenen Koordinaten die Zahlen zufolgenden Buchstaben:c = Cafe/Restaurantb = Eingang für Behinderteg = Gartensaalm = Museumsverwaltungs = Studiogalerie2. Gehe zu N 51°0 b , g (s-c-g) g' E 007°0 b , (s-c-g) mg'.An der Stirnseite dieses Bauwerks hängt eine Texttafel mit einigenJahreszahlen. Die letzte davon brauchst Du für die neueKoordinatenberechnung: (e = einer, z = zehner, h = hunderter, t =tausender)3. Finde bei N 51°0 (t+e), z (z-e-t) (h-e)' E 007°0 e, h(h-z)(z+e)' die Informationstafel zu dem merkwürdigen Teil undrechne mit dem Todesjahr weiter, das darauf angegeben ist4. Bei N 51°0 (t+t), (h-e) (h-e) (z+t)' E 007°0 t , (h-e) (e)(h-z+t)' ist von dem Gebäude, das hier einst stand nicht mehrviel übrig geblieben. Von der Texttafel wird die dritteJahreszahl benötigt.Bis zur, hoffentlich baldigen, Ersetzung der Messingplatte ist die vierstellige Zahl mit scharzem Edding auf die Holztafel gemalt.5. Bei N 51°0 h, z h e' E 007°0 t, z h (z*z-t)' ist einGebäude nicht zu übersehen. Am Giebel steht eine einsameJahreszahl, die die Koordinaten für die letzte Station liefert:6. Halte bei N 51°0 e , t (t+e+e) (t+e)' E 007°0 t, (h-t-e)(e+e) t' Ausschau nach einer der inzwischen vertrautenTexttafeln. Die zweite Jahreszahl darauf führt direkt zumCache:7. Finde den Cache bei N 51°0 (e-t) , t e e' E 007°0 t, (h-t)(h-z) (e+t)'© Rabe, Opencaching.de, CC BY-NC-ND, Stand: 24.04.2018;alle Logeinträge © jeweiliger Autor\",\"tips\":\"\",\"caseType\":\"normal\",\"difficulty\":2,\"condition\":\"ok\",\"name\":\"Alkenrath Sights von Rabe\",\"cacheType\":\"Multicache\",\"terrain\":2,\"logs\":[{\"date\":\" 15.02.2017 \",\"person\":\"Seebaer777 \",\"message\":\" Den Multi machte ich in ca. 3 Etappen, je nachdem, ob eine Station für eine anderweitig geplante Cachetour in der Nähe war oder nicht.An Station 3 startete ich die heutige Tour und radelte die bis zum Ende ab und nahm noch den einen oder anderen Tradi bzw. Multi mit.Vielen Dank fürs Zeigen der Gegend.Fast hätte ich hier nicht finalisieren können, da ich erst nicht den Deckel der \\\"Umverpackung\\\" öffnen konnte. Am Ende klappte es doch noch.\"},{\"date\":\" 09.04.2016 13:40 \",\"person\":\"Akkus \",\"message\":\" Schöne Runde mit dem Fahrrad und so lernt man auch Leverkusen kennen .  Eigentlich hatten wir ein wenig Angst vor dem ersten Multi aber es war einfacher als gedacht. Die größte Schwierigkeit war, den Cache zu öffnen aber auch das ist irgendwann gelungen. Das war der bis jetzt trockenste Cach-Inhalt, den wir bis jetzt gesehen haben.DfdCDie Akkus\"},{\"date\":\" 28.09.2015 15:45 \",\"person\":\"doppelcacher lev \",\"message\":\" beim zweiten Anlauf   ,beim ersten Anlauf überraschte uns die Dunkelheitdoppelcacher 1 hat heute zum ersten mal ohne Hilfe einen cache ,Danke für die schöne Runde\"},{\"date\":\" 30.07.2014 \",\"person\":\"Nordlandkai \",\"message\":\" Den Multi bin ich heute nach Feierabend angegangen.Am Start musste ich ein wenig Suchen bis ich die Infos hatte.Danach lief dann aber alles bestens. Schritt für Schritt, oder sagen wir besser Station für Station kam ich dem Finale näher bis ich schließlich die Koordinaten in der Hand hatte. Damit ging es dann zum Cache der sich auch dank prima Koordinaten sofort zeigte.Vielen Dank für das lange erhalten des Caches.Dafür vergebe ich gerne eine Empfehlung.\"},{\"date\":\" 20.10.2013 16:40 \",\"person\":\"Na+To \",\"message\":\" Einen wunderschönen Sonntagsnachmittagsausflug gewährte uns dieser Cache. Unser Durchhaltevermögen bei einem überraschenden herbstlichen Regenschauer wurde mit anschließender tollen Sonne belohnt. Leider ist das Logbuch ziemlich aufgeweicht, fällt auseinander und die Schutztüte ist zerrissen. Haben uns erlaubt sie gegen eine neue auszutauschen. Da auch sonst der Cache ziemlich \\\"geplündert\\\" war, haben wir ihn mal etwas aufgefüllt.  OUT: --- IN: Wasserball, Taschentücher, Kugelschreiber, NotizbuchEin Kontrollgang demnächst wäre evtl. angebracht. Alles in allem hat es uns trotzdem verdammt viel Spaß gemacht. Schließlich ist der Weg das Ziel! Viele schöne neue Eckchen entdeckt, eine klasse Tour, vielen Dank und LG!\"},{\"date\":\" 20.09.2013 \",\"person\":\"MaJoSaPe \",\"message\":\" Heute Loggen wir ein paar Caches nach die wir bei GC.com bereits  haben.Dieser Cache hat uns ca. 6 Monate beschäftigt. Wir haben hier immer mal wieder eine der Stationen besucht. Heute dann die letzte Station und das Final. DFDCmasape(heute MaJoSaPe)\"},{\"date\":\" 19.09.2013 17:30 \",\"person\":\"spock1701 \",\"message\":\" Man lernt doch immer noch etwas neues dazu. Vielen Dank für die schöne Runde.Spock1701\"},{\"date\":\" 08.09.2013 17:43 \",\"person\":\"Jaw1231 \",\"message\":\" Eine sehr schöne Runde, vielen Dank!In: EdelsteinOut: Nix\"},{\"date\":\" 29.06.2013 \",\"person\":\"Torald \",\"message\":\" Das war eine tolle Runde mit interessanten Stationen.Danke für den Multi :-)Torald\"},{\"date\":\" 15.06.2013 15:10 \",\"person\":\"lev_jung \",\"message\":\" Das war eine schöne Runde durch Alkenrath. Alle Stationen waren gut zu finden. DFDC\"},{\"date\":\" 21.04.2013 \",\"person\":\"Dä Tuppes \",\"message\":\" Eine schöne Runde in einem bekannten Stadtteil, aber an Station 5 und 6 war ich noch nie!TFTC   Dä Tuppes\"},{\"date\":\" 06.04.2013 \",\"person\":\"ajokoira \",\"message\":\" Heute war ich bei musm701 zu Besuch, und nach dem Essen haben wir einen kleinen Spaziergang (etwas über11 km ) in seiner Homezone gemacht.Da ich noch nicht genug hatte sind wir auch diese nette Runde gelaufen und am Ende konnte ich mich im Logbuch eintragen. Ein schänes Versteck ist das hier.Danke fürs verstecken und Grüße aus Rheinhessen \"},{\"date\":\" 26.03.2013 \",\"person\":\"Nekromiko \",\"message\":\" Hallöle,diese nette kleine Runde nach Feierabend gegangen.So ging es bei feinem Sonnenschein abwechslungsreich durch Stadt, Park, Wald und über diverse Wasser.Alle Stationen waren gut zu finden und die neuen Koordinaten schnell berechnet.Obwohl ich wegen einem \\\"Übertragungsfehlers\\\" einen Umweg gemacht habe, war ich in etwa 1 Stunde durch.Das Schild an Station 4 ist wieder vorhanden, der Hinweis kann aus dem Listing entfernt werden.Das Final ist leider wegen einem Loch im Boden recht feucht, der Inhalt teilweise schimmelig - das Logbuch ist auch klammDa könnte mal was Neues her...Ansonsten eine empfehlenswerte Runde, danke dafürRein: TB, PilzkerzeRaus: NixSchöne Grüße aus SGnekromiko\"},{\"date\":\" 02.03.2013 \",\"person\":\"Schadi \",\"message\":\" Dank dieses altehrwürdigen Multis kenne ich jetzt einen weiteren Ortsteil von Leverkusen! Bei herrlichem Vorfrühlingswetter bin ich heute die Runde entlang geschlendert und habe dabei die ersten Schneeglöckchen und Krokusse des Jahres entdeckt. Alle Stationen waren gut zu finden, ebenso wie die große Dose am Ziel (die bei Gelegenheit ersetzt werden sollte - der Boden ist gebrochen, lange geht das nicht mehr gut).Danke für den Cache, dem ich wünsche, dass er noch lange erhalten bleibt!\"},{\"date\":\" 15.12.2012 \",\"person\":\"Sirebolev \",\"message\":\" Interessante Stadtteilrunde.Danke an Rabe und Gruß an Alle!\"},{\"date\":\" 01.11.2012 \",\"person\":\"Birnemaja \",\"message\":\" Ein sehr schöner Spazierweg - auch bei Dauerregen \"},{\"date\":\" 25.07.2012 \",\"person\":\"MarVi \",\"message\":\" Haben den Cache am 25ten Juli  bei lockeren 30°C im Schatten.Die Tour hat echt Spaß gemacht und ist perfekt geeignet für Fahrräder.Nach einigen Brennesselverbrennungen haben wir das Ziel doch endlich erreicht.  \"},{\"date\":\" 16.06.2012 \",\"person\":\"Black300880 \",\"message\":\" Trotz des Regenwetters war es eine schöne Runde. Danke fürs zeigen\"},{\"date\":\" 05.05.2011 \",\"person\":\"malf4u \",\"message\":\" Bei der kleinen Radtour durch Alkenrath wurden die Stationen gut  und die Aufgaben zügig gelöst.Danke für die schöne Runde, hat Spaß gemacht   :)    :)  malf4uIn : CoinOut: TB\"},{\"date\":\" 05.05.2011 \",\"person\":\"Dynamitgeco \",\"message\":\" Heute bei schönem Wetter mit dem Rad diese schöne Runde durch Alkenrath gedreht und so wiedermal Eindrücke gewonnen, die ich ohne Geocaching so nicht gemacht hätte. Eine witzige Sache war der Eierautomat, den ich in der nähe von Station 1 entdeckt habe. Es hat Spaß gemacht!  :)  DFDC.IN: GeoKretyOUT: GeoCoin\"}],\"descriptionSnippet\":\"Kurzer Rundgang zu Alkenrather Sehenswürdigkeiten.\",\"status\":\"kann gesucht werden\"}";
	}
	
}
