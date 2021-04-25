import java.util.Scanner;

public class Country {


        public static void topCountryHeadlines () {
            int i;
            String[] topCountryList = {"ae", "ar", "at", "au", "be", "bg", "br", "ca", "ch", "cn", "co", "cu", "cz", "de", "eg", "fr", "gb", "gr", "hk", "hu", "id", "ie", "il", "in", "it", "jp", "kr", "lt", "lv", "ma", "mx", "my", "ng", "nl", "no", "nz", "ph", "pl", "pt", "ro", "rs", "ru", "sa", "se", "sg", "si", "sk", "th", "tr", "tw", "ua", "us", "ve", "za"};

            //possible country tags not yet implemented
            //list of possible countries [ae, ar, at, au, be, bg, br, ca, ch, cn, co, cu, cz, de, eg, fr, gb, gr, hk, hu,
            // id, ie, il, in, it, jp, kr, lt, lv, ma, mx, my, ng, nl, no, nz, ph, pl, pt, ro, rs, ru, sa, se, sg, si, sk,
            // th, tr, tw, ua, us, ve, za]
            Scanner in = new Scanner(System.in);
            String country = in.next();
            for (i = 0; i < topCountryList.length; i++) {
                if (country.equals(topCountryList[i])) {
                    GUIMain.info;
                } else {
                    //maybe an error message will pop up then call country again
                    topCountryHeadlines();
                }
            }

            API_Translator translator = new API_Translator();

            JSONObject topUSHeadlines = translator.getAllTopHeadlinesForCountry(country);
            Main.printArticles(topUSHeadlines);
            // i just did that so it had no errors.
        }
    }
