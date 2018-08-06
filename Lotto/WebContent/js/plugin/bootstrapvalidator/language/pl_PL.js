/*! SmartAdmin - v1.5 - 2014-09-27 */

!function(a) {
    a.fn.bootstrapValidator.i18n=a.extend(!0, a.fn.bootstrapValidator.i18n, {
        "base64": {
            "default": "Wpisz poprawny ci\u0105g znak\xf3w zakodowany w base 64"
        }
        , "between": {
            "default": "Wprowad\u017a warto\u015b\u0107 pomi\u0119dzy %s i %s", "notInclusive": "Wprowad\u017a warto\u015b\u0107 pomi\u0119dzy %s i %s (zbi\xf3r otwarty)"
        }
        , "callback": {
            "default": "Wprowad\u017a poprawn\u0105 warto\u015b\u0107"
        }
        , "choice": {
            "default": "Wprowad\u017a poprawn\u0105 warto\u015b\u0107", "less": "Wybierz przynajmniej %s opcji", "more": "Wybierz maksymalnie %s opcji", "between": "Wybierz przynajmniej %s i maksymalnie %s opcji"
        }
        , "creditCard": {
            "default": "Wprowad\u017a poprawny numer karty kredytowej"
        }
        , "cusip": {
            "default": "Wprowad\u017a poprawny numer CUSIP"
        }
        , "cvv": {
            "default": "Wprowad\u017a poprawny numer CVV"
        }
        , "date": {
            "default": "Wprowad\u017a poprawn\u0105 dat\u0119"
        }
        , "different": {
            "default": "Wprowad\u017a inn\u0105 warto\u015b\u0107"
        }
        , "digits": {
            "default": "Wprowad\u017a tylko cyfry"
        }
        , "ean": {
            "default": "Wprowad\u017a poprawny numer EAN"
        }
        , "emailAddress": {
            "default": "Wprowad\u017a poprawny adres e-mail"
        }
        , "file": {
            "default": "Wybierz prawid\u0142owy plik"
        }
        , "greaterThan": {
            "default": "Wprowad\u017a warto\u015b\u0107 wi\u0119ksz\u0105 b\u0105d\u017a r\xf3wn\u0105 %s", "notInclusive": "Wprowad\u017a warto\u015b\u0107 wi\u0119ksz\u0105 ni\u017c %s"
        }
        , "grid": {
            "default": "Wprowad\u017a poprawny numer GRId"
        }
        , "hex": {
            "default": "Wprowad\u017a poprawn\u0105 liczb\u0119 w formacie heksadecymalnym"
        }
        , "hexColor": {
            "default": "Wprowad\u017a poprawny kolor w formacie hex"
        }
        , "iban": {
            "default":"Wprowad\u017a poprawny numer IBAN", "countryNotSupported":"Kod kraju %s nie jest obs\u0142ugiwany", "country":"Wprowad\u017a poprawny numer IBAN w kraju %s", "countries": {
                "AD": "Andora", "AE": "Zjednoczone Emiraty Arabskie", "AL": "Albania", "AO": "Angola", "AT": "Austria", "AZ": "Azerbejd\u017can", "BA": "Bo\u015bnia i Hercegowina", "BE": "Belgia", "BF": "Burkina Faso", "BG": "Bu\u0142garia", "BH": "Bahrajn", "BI": "Burundi", "BJ": "Benin", "BR": "Brazylia", "CH": "Szwajcaria", "CI": "Wybrze\u017ce Ko\u015bci S\u0142oniowej", "CM": "Kamerun", "CR": "Kostaryka", "CV": "Republika Zielonego Przyl\u0105dka", "CY": "Cypr", "CZ": "Czechy", "DE": "Niemcy", "DK": "Dania", "DO": "Dominikana", "DZ": "Algeria", "EE": "Estonia", "ES": "Hiszpania", "FI": "Finlandia", "FO": "Wyspy Owcze", "FR": "Francja", "GB": "Wielka Brytania", "GE": "Gruzja", "GI": "Gibraltar", "GL": "Grenlandia", "GR": "Grecja", "GT": "Gwatemala", "HR": "Chorwacja", "HU": "W\u0119gry", "IE": "Irlandia", "IL": "Izrael", "IR": "Iran", "IS": "Islandia", "IT": "W\u0142ochy", "JO": "Jordania", "KW": "Kuwejt", "KZ": "Kazahstan", "LB": "Liban", "LI": "Liechtenstein", "LT": "Litwa", "LU": "Luksemburg", "LV": "\u0141otwa", "MC": "Monako", "MD": "Mo\u0142dawia", "ME": "Czarnog\xf3ra", "MG": "Madagaskar", "MK": "Macedonia", "ML": "Mali", "MR": "Mauretania", "MT": "Malta", "MU": "Mauritius", "MZ": "Mozambik", "NL": "Holandia", "NO": "Norwegia", "PK": "Pakistan", "PL": "Polska", "PS": "Palestyna", "PT": "Portugalia", "QA": "Katar", "RO": "Rumunia", "RS": "Serbia", "SA": "Arabia Saudyjska", "SE": "Szwecja", "SI": "S\u0142owenia", "SK": "S\u0142owacja", "SM": "San Marino", "SN": "Senegal", "TN": "Tunezja", "TR": "Turcja", "VG": "Brytyjskie Wyspy Dziewicze"
            }
        }
        , "id": {
            "default":"Wprowad\u017a poprawny numer identyfikacyjny", "countryNotSupported":"Kod kraju %s nie jest obs\u0142ugiwany", "country":"Wprowad\u017a poprawny %s numer identyfikacyjny", "countries": {
                "BA": "bo\u015bniacki", "BG": "bu\u0142garski", "BR": "brazylijski", "CH": "szwecki", "CL": "czilijski", "CZ": "czeski", "DK": "du\u0144ski", "EE": "esto\u0144ski", "ES": "hiszpa\u0144ski", "FI": "fi\u0144ski", "HR": "chorwacki", "IE": "irlandzki", "IS": "islandski", "LT": "litewski", "LV": "\u0142otewski", "ME": "czarnog\xf3rski", "MK": "macedo\u0144ski", "NL": "holenderski", "RO": "rumu\u0144ski", "RS": "serbski", "SE": "szwed\u017aki", "SI": "s\u0142owe\u0144ski", "SK": "s\u0142owacki", "SM": "san Marino", "ZA": "po\u0142udniowo Afryka\u0144ski"
            }
        }
        , "identical": {
            "default": "Wprowad\u017a tak\u0105 sam\u0105 warto\u015b\u0107"
        }
        , "imei": {
            "default": "Wprowad\u017a poprawny numer IMEI"
        }
        , "imo": {
            "default": "Wprowad\u017a poprawny numer IMO"
        }
        , "integer": {
            "default": "Wprowad\u017a poprawn\u0105 liczb\u0119 ca\u0142kowit\u0105"
        }
        , "ip": {
            "default": "Wprowad\u017a poprawny adres IP", "ipv4": "Wprowad\u017a poprawny adres IPv4", "ipv6": "Wprowad\u017a poprawny adres IPv6"
        }
        , "isbn": {
            "default": "Wprowad\u017a poprawny numer ISBN"
        }
        , "isin": {
            "default": "Wprowad\u017a poprawny numer ISIN"
        }
        , "ismn": {
            "default": "Wprowad\u017a poprawny numer ISMN"
        }
        , "issn": {
            "default": "Wprowad\u017a poprawny numer ISSN"
        }
        , "lessThan": {
            "default": "Wprowad\u017a warto\u015b\u0107 mniejsz\u0105 b\u0105d\u017a r\xf3wn\u0105 %s", "notInclusive": "Wprowad\u017a warto\u015b\u0107 mniejsz\u0105 ni\u017c %s"
        }
        , "mac": {
            "default": "Wprowad\u017a poprawny adres MAC"
        }
        , "meid": {
            "default": "Wprowad\u017a poprawny numer MEID"
        }
        , "notEmpty": {
            "default": "Wprowad\u017a warto\u015b\u0107, pole nie mo\u017ce by\u0107 puste"
        }
        , "numeric": {
            "default": "Wprowad\u017a poprawn\u0105 liczb\u0119 zmiennoprzecinkow\u0105"
        }
        , "phone": {
            "default":"Wprowad\u017a poprawny numer telefonu", "countryNotSupported":"Kod kraju %s nie jest wspierany", "country":"Wprowad\u017a poprawny numer telefonu w kraju %s", "countries": {
                "BR": "Brazylia", "ES": "Hiszpania", "FR": "Francja", "GB": "Wielka Brytania", "MA": "Maroko", "PK": "Pakistan", "US": "USA"
            }
        }
        , "regexp": {
            "default": "Wprowad\u017a warto\u015b\u0107 pasuj\u0105c\u0105 do wzoru"
        }
        , "remote": {
            "default": "Wprowad\u017a poprawn\u0105 warto\u015b\u0107"
        }
        , "rtn": {
            "default": "Wprowad\u017a poprawny numer RTN"
        }
        , "sedol": {
            "default": "Wprowad\u017a poprawny numer SEDOL"
        }
        , "siren": {
            "default": "Wprowad\u017a poprawny numer SIREN"
        }
        , "siret": {
            "default": "Wprowad\u017a poprawny numer SIRET"
        }
        , "step": {
            "default": "Wprowad\u017a wielokrotno\u015b\u0107 %s"
        }
        , "stringCase": {
            "default": "Wprowad\u017a tekst sk\u0142adaj\u0105c\u0105 si\u0119 tylko z ma\u0142ych liter", "upper": "Wprowad\u017a tekst sk\u0142adaj\u0105cy si\u0119 tylko z du\u017cych liter"
        }
        , "stringLength": {
            "default": "Wprowad\u017a warto\u015b\u0107 o poprawnej d\u0142ugo\u015bci", "less": "Wprowad\u017a mniej ni\u017c %s znak\xf3w", "more": "Wprowad\u017a wi\u0119cej ni\u017c %s znak\xf3w", "between": "Wprowad\u017a warto\u015b\u0107 sk\u0142adaj\u0105ca si\u0119 z min %s i max %s znak\xf3w"
        }
        , "uri": {
            "default": "Wprowad\u017a poprawny URI"
        }
        , "uuid": {
            "default": "Wprowad\u017a poprawny numer UUID", "version": "Wprowad\u017a poprawny numer UUID w wersji %s"
        }
        , "vat": {
            "default":"Wprowad\u017a poprawny numer VAT", "countryNotSupported":"Kod kraju %s nie jest wsperany", "country":"Wprowad\u017a poprawny %s numer VAT", "countries": {
                "AT": "austryjacki", "BE": "belgijski", "BG": "bu\u0142garski", "BR": "brazylijski", "CH": "szwecki", "CY": "cypryjski", "CZ": "czeski", "DE": "niemiecki", "DK": "du\u0144ski", "EE": "esto\u0144ski", "ES": "hiszpa\u0144ski", "FI": "fi\u0144ski", "FR": "francuski", "GB": "brytyjski", "GR": "grecki", "EL": "grecki", "HU": "w\u0119gierski", "HR": "chorwacki", "IE": "irlandzki", "IS": "Islandia", "IT": "w\u0142oski", "LT": "litewski", "LU": "luksemburski", "LV": "\u0142oteweski", "MT": "maltejski", "NL": "holenderski", "NO": "norweski", "PL": "polski", "PT": "portugalski", "RO": "rumu\u0144ski", "RU": "rosyjski", "RS": "serbski", "SE": "szwedzki", "SI": "s\u0142owe\u0144ski", "SK": "s\u0142owacki", "ZA": "po\u0142udniowo Afryka\u0144ski"
            }
        }
        , "vin": {
            "default": "Wprowad\u017a poprawny numer VIN"
        }
        , "zipCode": {
            "default":"Wprowad\u017a poprawny kod pocztowy", "countryNotSupported":"Kod kraju %s nie jest obs\u0142ugiwany", "country":"Wprowad\u017a poprawny %s kod pocztowy", "countries": {
                "BR": "brazylijski", "CA": "kanadyski", "DK": "du\u0144ski", "GB": "brytyjski", "IT": "w\u0142oski", "MA": "Maroko", "NL": "holenderski", "SE": "szwecki", "SG": "singapurski", "US": "w USA"
            }
        }
    }
    )
}

(window.jQuery);