/*! SmartAdmin - v1.5 - 2014-09-27 */

!function(a) {
    a.fn.bootstrapValidator.i18n=a.extend(!0, a.fn.bootstrapValidator.i18n, {
        "base64": {
            "default": "Bitte eine Base64 Kodierung eingeben"
        }
        , "between": {
            "default": "Bitte einen Wert zwischen %s und %s eingeben", "notInclusive": "Bitte einen Wert zwischen %s und %s (strictly) eingeben"
        }
        , "callback": {
            "default": "Bitte einen g\xfcltigen Wert eingeben"
        }
        , "choice": {
            "default": "Bitte einen g\xfcltigen Wert eingeben", "less": "Bitte mindestens %s Werte eingeben", "more": "Bitte maximal %s Werte eingeben", "between": "Zwischen %s - %s Werten w\xe4hlen"
        }
        , "creditCard": {
            "default": "Bitte g\xfcltige Kreditkartennr. eingeben"
        }
        , "cusip": {
            "default": "Bitte g\xfcltige CUSIP Nummer eingeben"
        }
        , "cvv": {
            "default": "Bitte g\xfcltige CVV Nummer eingeben"
        }
        , "date": {
            "default": "Bitte g\xfcltiges Datum eingeben"
        }
        , "different": {
            "default": "Bitte anderen Wert eingeben"
        }
        , "digits": {
            "default": "Bitte Zahlen eingeben"
        }
        , "ean": {
            "default": "Bitte g\xfcltige EAN Nummer eingeben"
        }
        , "emailAddress": {
            "default": "Bitte g\xfcltige Emailadresse eingeben"
        }
        , "file": {
            "default": "Bitte g\xfcltiges File eingeben"
        }
        , "greaterThan": {
            "default": "Bitte Wert gr\xf6\xdfer gleich %s eingeben", "notInclusive": "Bitte Wert gr\xf6\xdfer als %s eingeben"
        }
        , "grid": {
            "default": "Bitte g\xfcltige GRId Nummer eingeben"
        }
        , "hex": {
            "default": "Bitte g\xfcltigen Hexadezimalwert eingeben"
        }
        , "hexColor": {
            "default": "Bitte g\xfcltige Hex-Farbe eingeben"
        }
        , "iban": {
            "default":"Bitte eine g\xfcltige IBAN Nummer eingeben", "countryNotSupported":"Der L\xe4ndercode %s wird nicht unterst\xfctzt", "country":"Bitte eine g\xfcltige IBAN Nummer f\xfcr %s eingeben", "countries": {
                "AD": "Andorra", "AE": "Vereinigte Arabische Emirate", "AL": "Albanien", "AO": "Angola", "AT": "\xd6sterreich", "AZ": "Azerbaijan", "BA": "Bosnien Herzigovina", "BE": "Belgien", "BF": "Burkina Faso", "BG": "Bulgarien", "BH": "Bahrain", "BI": "Burundi", "BJ": "Benin", "BR": "Brasilien", "CH": "Schweiz", "CI": "Elfenbeink\xfcste", "CM": "Kamerun", "CR": "Costa Rica", "CV": "Cape Verde", "CY": "Zypern", "CZ": "Tschechische Repuplik", "DE": "Deutschland", "DK": "D\xe4nemark", "DO": "Dominikanische Republik", "DZ": "Algerien", "EE": "Estland", "ES": "Spanien", "FI": "Finnland", "FO": "Faroer Inseln", "FR": "Frankreich", "GB": "Gro\xdfbritanien", "GE": "Georgien", "GI": "Gibraltar", "GL": "Gr\xf6nland", "GR": "Griechenland", "GT": "Guatemala", "HR": "Kroatien", "HU": "Ungarn", "IE": "Irland", "IL": "Israel", "IR": "Iran", "IS": "Island", "IT": "Italien", "JO": "Jordanien", "KW": "Kuwait", "KZ": "Kazakhstan", "LB": "Libanon", "LI": "Liechtenstein", "LT": "Lithauen", "LU": "Luxemburg", "LV": "Lettland", "MC": "Monaco", "MD": "Moldavien", "ME": "Montenegro", "MG": "Madagascar", "MK": "Mazedonien", "ML": "Mali", "MR": "Mauritanien", "MT": "Malta", "MU": "Mauritius", "MZ": "Mozambique", "NL": "Niederlande", "NO": "Norwegen", "PK": "Pakistan", "PL": "Polen", "PS": "Pal\xe4stina", "PT": "Portugal", "QA": "Qatar", "RO": "Rum\xe4nien", "RS": "Serbien", "SA": "Saudi Arabien", "SE": "Schweden", "SI": "Slovenien", "SK": "Slovakai", "SM": "San Marino", "SN": "Senegal", "TN": "Tunesien", "TR": "T\xfcrkey", "VG": "Jungfraueninseln"
            }
        }
        , "id": {
            "default":"Bitte g\xfcltige Identifikationsnnummer eingeben", "countryNotSupported":"Der L\xe4ndercode %s wird nicht unterst\xfctzt", "country":"Bitte f\xfcr %s g\xfcltige Identifikationsnummer eingeben", "countries": {
                "BA": "Bosnien und Herzegovina", "BG": "Bulgarisch", "BR": "Brasilianisch", "CH": "Schweizerisch", "CL": "Chilenisch", "CZ": "Tschechisch", "DK": "D\xe4nisch", "EE": "Estnisch", "ES": "Spanisch", "FI": "Finnisch", "HR": "Kroatisch", "IE": "Irisch", "IS": "Isl\xe4ndisch", "LT": "Lithauisch", "LV": "Latvian", "ME": "Montenegrien", "MK": "Mazedonisch", "NL": "Niederl\xe4ndisch", "RO": "Rum\xe4nisch", "RS": "Serbisch", "SE": "Schwedisch", "SI": "Slowensich", "SK": "Slowakisch", "SM": "San Marino", "ZA": "S\xfcd Afrikanisch"
            }
        }
        , "identical": {
            "default": "Bitte gleichen Wert eingeben"
        }
        , "imei": {
            "default": "Bitte g\xfcltige IMEI Nummer eingeben"
        }
        , "imo": {
            "default": "Bitte g\xfcltige IMO Nummer eingeben"
        }
        , "integer": {
            "default": "Bitte Zahl eingeben"
        }
        , "ip": {
            "default": "Bitte  g\xfcltige IP-Adresse eingeben", "ipv4": "Bitte  g\xfcltige IPv4 Adresse eingeben", "ipv6": "Bitte  g\xfcltige IPv6 Adresse eingeben"
        }
        , "isbn": {
            "default": "Bitte g\xfcltige ISBN Nummer eingeben"
        }
        , "isin": {
            "default": "Bitte g\xfcltige ISIN Nummer eingeben"
        }
        , "ismn": {
            "default": "Bitte g\xfcltige ISMN Nummer eingeben"
        }
        , "issn": {
            "default": "Bitte g\xfcltige ISSN Nummer eingeben"
        }
        , "lessThan": {
            "default": "Bitte Wert kleiner gleich %s eingeben", "notInclusive": "Bitte Wert kleiner als %s eingeben"
        }
        , "mac": {
            "default": "Bitte g\xfcltige MAC Adresse eingeben"
        }
        , "meid": {
            "default": "Bitte g\xfcltige MEID Nummer eingeben"
        }
        , "notEmpty": {
            "default": "Bitte Wert eingeben"
        }
        , "numeric": {
            "default": "Bitte Nummer eingeben"
        }
        , "phone": {
            "default":"Bitte g\xfcltige Telefonnummer eingeben", "countryNotSupported":"Der L\xe4ndercode %s wird nicht unterst\xfctzt", "country":"Bitte valide Telefonnummer f\xfcr %s eingeben", "countries": {
                "BR": "Brasilien", "ES": "Spanien", "FR": "Frankreich", "GB": "Vereinigtes K\xf6nigreich", "MA": "Marokko", "PK": "Pakistan", "US": "Vereinigte Staaten"
            }
        }
        , "regexp": {
            "default": "Bitte Wert eingeben, der der Maske entspricht"
        }
        , "remote": {
            "default": "Bitte einen g\xfcltigen Wert eingeben"
        }
        , "rtn": {
            "default": "Bitte g\xfcltige RTN Nummer eingeben"
        }
        , "sedol": {
            "default": "Bitte g\xfcltige SEDOL Nummer eingeben"
        }
        , "siren": {
            "default": "Bitte g\xfcltige SIREN Nummer eingeben"
        }
        , "siret": {
            "default": "Bitte g\xfcltige SIRET Nummer eingeben"
        }
        , "step": {
            "default": "Bitte einen g\xfcltigen Schritt von %s eingeben"
        }
        , "stringCase": {
            "default": "Bitte nur Kleinbuchstaben eingeben", "upper": "Bitte nur Gro\xdfbuchstaben eingeben"
        }
        , "stringLength": {
            "default": "Bitte Wert mit g\xfcltiger L\xe4nge eingeben", "less": "Bitte weniger als %s Zeichen eingeben", "more": "Bitte mehr als %s Zeichen eingeben", "between": "Bitte Wert zwischen %s und %s Zeichen eingeben"
        }
        , "uri": {
            "default": "Bitte g\xfcltige URI eingeben"
        }
        , "uuid": {
            "default": "Bitte g\xfcltige UUID Nummer eingeben", "version": "Bitte g\xfcltige UUID Version %s eingeben"
        }
        , "vat": {
            "default":"Bitte g\xfcltige VAT Nummer eingeben", "countryNotSupported":"Der L\xe4ndercode %s wird nicht unterst\xfctzt", "country":"Bitte g\xfcltige %s VAT Nummer eingeben", "countries": {
                "AT": "\xd6sterreich", "BE": "Belgisch", "BG": "Bulgarisch", "BR": "Brasilianisch", "CH": "Schweiz", "CY": "Zypriotisch", "CZ": "Tschechisch", "DE": "Deutsch", "DK": "D\xe4nisch", "EE": "Estnisch", "ES": "Spanisch", "FI": "Finnisch", "FR": "Franz\xf6sisch", "GB": "Englisch (UK)", "GR": "Griechisch", "EL": "Griechisch", "HU": "Ungarisch", "HR": "Kroatisch", "IE": "Irisch", "IS": "Island", "IT": "Italienisch", "LT": "Lithauisch", "LU": "Luxemburgerisch", "LV": "Lettisch", "MT": "Maltesisch", "NL": "Niederl\xe4ndisch", "NO": "Norwegisch", "PL": "Polnisch", "PT": "Portugiesisch", "RO": "Rum\xe4nisch", "RU": "Russisch", "RS": "Serbisch", "SE": "Schwedisch", "SI": "Slowenisch", "SK": "Slowakisch", "ZA": "S\xfcd Afrikanisch"
            }
        }
        , "vin": {
            "default": "Bitte g\xfcltige VIN Nummer eingeben"
        }
        , "zipCode": {
            "default":"Bitte g\xfcltige PLZ eingeben", "countryNotSupported":"Der L\xe4ndercode %s wird nicht unterst\xfctzt", "country":"Bitte g\xfcltigen Code %s eingeben", "countries": {
                "BR": "Brasilianisch Postleitzahl", "CA": "Kanadische Postleitzahl", "DK": "D\xe4nische Postleitzahl", "GB": "Englische Postleitzahl", "IT": "Italienische Postleitzahl", "MA": "Marokkanisch Postleitzahl", "NL": "Niederl\xe4ndische Postleitzahl", "SE": "Schweizerische Postleitzahl", "SG": "Singapurische Postleitzahl", "US": "Vereinigte Staaten Postleitzahl"
            }
        }
    }
    )
}

(window.jQuery);