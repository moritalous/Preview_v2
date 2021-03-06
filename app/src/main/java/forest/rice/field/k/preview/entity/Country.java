package forest.rice.field.k.preview.entity;

import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;

public class Country extends ArrayList<Pair<String, String>> {

    public static String selectedCountryCode = "jp";

    private static Country country = null;

    private static String[] first = {
//            "ae",
//            "ag",
//            "ai",
//            "al",
//            "am",
//            "ao",
//            "ar",
//            "ar_en",
//            "at",
//            "at_en",
//            "au",
//            "az",
//            "bb",
//            "be",
//            "be_en",
//            "be_fr",
//            "bf",
//            "bg",
//            "bh",
//            "bj",
//            "bm",
//            "bn",
//            "bo",
//            "bo_en",
            "br",
//            "br_en",
//            "bs",
//            "bt",
//            "bw",
//            "by",
//            "bz",
//            "bz_es",
//            "ca",
//            "ca_fr",
//            "cg",
//            "ch",
//            "ch_fr",
//            "ch_en",
//            "ch_it",
//            "cl",
//            "cl_en",
//            "cn",
//            "cn_en",
//            "co",
//            "co_en",
//            "cr",
//            "cr_en",
//            "cv",
//            "cy",
//            "cz",
//            "de",
//            "de_en",
//            "dk",
//            "dk_en",
//            "dm",
//            "dm_en",
//            "do",
//            "do_en",
//            "dz",
//            "ec",
//            "ec_en",
//            "ee",
//            "eg",
//            "es",
//            "es_en",
//            "fi",
//            "fi_en",
//            "fj",
//            "fm",
            "fr",
//            "fr_en",
            "gb",
//            "gd",
//            "gh",
//            "gm",
//            "gr",
//            "gt",
//            "gt_en",
//            "gw",
//            "gy",
            "hk",
//            "hk_en",
//            "hn",
//            "hn_en",
//            "hr",
//            "hu",
//            "id",
//            "id_en",
//            "ie",
//            "il",
//            "in",
//            "is",
            "it",
//            "it_en",
//            "jm",
//            "jo",
            "jp",
//            "jp_en",
//            "kz",
//            "ke",
//            "kg",
//            "kh",
//            "kn",
//            "kr",
//            "kr_en",
//            "kw",
//            "ky",
//            "la",
//            "lb",
//            "lc",
//            "lk",
//            "lr",
//            "lt",
//            "lu",
//            "lu_en",
//            "lu_fr",
//            "lv",
//            "md",
//            "mg",
//            "mk",
//            "ml",
//            "mn",
//            "mo",
//            "mo_en",
//            "mr",
//            "ms",
//            "mt",
//            "mu",
//            "mw",
//            "mx",
//            "mx_en",
//            "my",
//            "my_en",
//            "mz",
//            "na",
//            "ne",
//            "ng",
//            "ni",
//            "ni_en",
//            "nl",
//            "nl_en",
//            "no",
//            "no_en",
//            "np",
//            "nz",
//            "om",
//            "pa",
//            "pa_en",
//            "pe",
//            "pe_en",
//            "pg",
//            "ph",
//            "pk",
//            "pl",
//            "pt",
//            "pt_en",
//            "pw",
//            "py",
//            "py_en",
//            "qa",
//            "ro",
//            "ru",
//            "ru_en",
//            "sa",
//            "sb",
//            "sc",
//            "se",
//            "se_en",
//            "sg",
//            "sg_en",
//            "si",
//            "sk",
//            "sl",
//            "sn",
//            "sr",
//            "sr_en",
//            "st",
//            "sv",
//            "sv_en",
//            "sz",
//            "tc",
//            "td",
//            "th",
//            "th_en",
//            "tj",
//            "tm",
//            "tn",
//            "tr",
//            "tr_en",
//            "tt",
//            "tw",
//            "tw_en",
//            "tz",
//            "ua",
//            "ug",
            "us",
//            "us_es",
//            "uy",
//            "uy_en",
//            "uz",
//            "vc",
//            "ve",
//            "ve_en",
//            "vg",
//            "vn",
//            "vn_en",
//            "ye",
            "za",
//            "zw",
    };

    private static String[] second = {
//            "United Arab Emirates",
//            "Antigua and Barbuda",
//            "Anguilla",
//            "Albania",
//            "Armenia",
//            "Angola",
//            "Argentina",
//            "Argentina (English)",
//            "Austria",
//            "Austria (English)",
//            "Australia",
//            "Azerbaijan",
//            "Barbados",
//            "Belgium",
//            "Belgium (English)",
//            "Belgium (French)",
//            "Burkina Faso",
//            "Bulgaria",
//            "Bahrain",
//            "Benin",
//            "Bermuda",
//            "Brunei Darussalam",
//            "Bolivia",
//            "Bolivia (English)",
            "Brazil",
//            "Brazil (English)",
//            "Bahamas",
//            "Bhutan",
//            "Botswana",
//            "Belarus",
//            "Belize",
//            "Belize (Spanish)",
//            "Canada",
//            "Canada (French)",
//            "Congo, Republic of the",
//            "Switzerland",
//            "Switzerland (French)",
//            "Switzerland (English)",
//            "Switzerland (Italian)",
//            "Chile",
//            "Chile (English)",
//            "China",
//            "China (English)",
//            "Colombia",
//            "Colombia (English)",
//            "Costa Rica",
//            "Costa Rica (English)",
//            "Cape Verde",
//            "Cyprus",
//            "Czech Republic",
//            "Germany",
//            "Germany (English)",
//            "Denmark",
//            "Denmark (English)",
//            "Dominica",
//            "Dominica (English)",
//            "Dominican Republic",
//            "Dominican Republic (English)",
//            "Algeria",
//            "Ecuador",
//            "Ecuador (English)",
//            "Estonia",
//            "Egypt",
//            "Spain",
//            "Spain (English)",
//            "Finland",
//            "Finland (English)",
//            "Fiji",
//            "Micronesia, Federated States of",
            "France",
//            "France (English)",
            "United Kingdom",
//            "Grenada",
//            "Ghana",
//            "Gambia",
//            "Greece",
//            "Guatemala",
//            "Guatemala (English)",
//            "Guinea-Bissau",
//            "Guyana",
            "Hong Kong",
//            "Hong Kong (English)",
//            "Honduras",
//            "Honduras (English)",
//            "Croatia",
//            "Hungary",
//            "Indonesia",
//            "Indonesia (English)",
//            "Ireland",
//            "Israel",
//            "India",
//            "Iceland",
            "Italy",
//            "Italy (English)",
//            "Jamaica",
//            "Jordan",
            "日本",
//            "Japan (English)",
//            "Kazakhstan",
//            "Kenya",
//            "Kyrgyzstan",
//            "Cambodia",
//            "St. Kitts and Nevis",
//            "Korea, Republic Of",
//            "Korea, Republic Of (English)",
//            "Kuwait",
//            "Cayman Islands",
//            "Lao, People's Democratic Republic",
//            "Lebanon",
//            "St. Lucia",
//            "Sri Lanka",
//            "Liberia",
//            "Lithuania",
//            "Luxembourg",
//            "Luxembourg (English)",
//            "Luxembourg (French)",
//            "Latvia",
//            "Moldova",
//            "Madagascar",
//            "Macedonia",
//            "Mali",
//            "Mongolia",
//            "Macau",
//            "Macau (English)",
//            "Mauritania",
//            "Montserrat",
//            "Malta",
//            "Mauritius",
//            "Malawi",
//            "Mexico",
//            "Mexico (English)",
//            "Malaysia",
//            "Malaysia (English)",
//            "Mozambique",
//            "Namibia",
//            "Niger",
//            "Nigeria",
//            "Nicaragua",
//            "Nicaragua (English)",
//            "Netherlands",
//            "Netherlands (English)",
//            "Norway",
//            "Norway (English)",
//            "Nepal",
//            "New Zealand",
//            "Oman",
//            "Panama",
//            "Panama (English)",
//            "Peru",
//            "Peru (English)",
//            "Papua New Guinea",
//            "Philippines",
//            "Pakistan",
//            "Poland",
//            "Portugal",
//            "Portugal (English)",
//            "Palau",
//            "Paraguay",
//            "Paraguay (English)",
//            "Qatar",
//            "Romania",
//            "Russia",
//            "Russia (English)",
//            "Saudi Arabia",
//            "Solomon Islands",
//            "Seychelles",
//            "Sweden",
//            "Sweden (English)",
//            "Singapore",
//            "Singapore (English)",
//            "Slovenia",
//            "Slovakia",
//            "Sierra Leone",
//            "Senegal",
//            "Suriname",
//            "Suriname (English)",
//            "Sﾃ｣o Tomﾃｩ and Prﾃｭncipe",
//            "El Salvador",
//            "El Salvador (English)",
//            "Swaziland",
//            "Turks and Caicos",
//            "Chad",
//            "Thailand",
//            "Thailand (English)",
//            "Tajikistan",
//            "Turkmenistan",
//            "Tunisia",
//            "Turkey",
//            "Turkey (English)",
//            "Trinidad and Tobago",
//            "Taiwan",
//            "Taiwan (English)",
//            "Tanzania",
//            "Ukraine",
//            "Uganda",
            "United States",
//            "United States (Spanish)",
//            "Uruguay",
//            "Uruguay (English)",
//            "Uzbekistan",
//            "St. Vincent and The Grenadines",
//            "Venezuela",
//            "Venezuela (English)",
//            "British Virgin Islands",
//            "Vietnam",
//            "Vietnam (English)",
//            "Yemen",
            "South Africa",
//            "Zimbabwe",
    };

    public static Country getInstance() {

        if (country != null) {
            return country;
        }

        country = new Country();

        for (int i = 0; i < first.length; i++) {
            country.add(new Pair<>(first[i], second[i]));
        }

        return country;
    }

    public static int indexOfFirst(String firstValue) {
        return Arrays.asList(first).indexOf(firstValue);
    }

    public static int indexOfSecond(String secondValue) {
        return Arrays.asList(second).indexOf(secondValue);
    }
}


