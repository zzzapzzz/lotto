/*! SmartAdmin - v1.5 - 2014-09-27 */

!function(a) {
    a.fn.bootstrapValidator.i18n=a.extend(!0, a.fn.bootstrapValidator.i18n, {
        "base64": {
            "default": "L\xfctfen 64 bit taban\u0131na uygun bir giri\u015f yap\u0131n\u0131z"
        }
        , "between": {
            "default": "L\xfctfen %s ile %s aras\u0131nda bir de\u011fer giriniz", "notInclusive": "L\xfctfen sadece %s ile %s aras\u0131nda bir de\u011fer giriniz"
        }
        , "callback": {
            "default": "L\xfctfen ge\xe7erli bir de\u011fer giriniz"
        }
        , "choice": {
            "default": "L\xfctfen ge\xe7erli bir de\u011fer giriniz", "less": "L\xfctfen minimum %s kadar de\u011fer giriniz", "more": "L\xfctfen maksimum %s kadar de\u011fer giriniz", "between": "L\xfctfen %s - %s aras\u0131 se\xe7iniz"
        }
        , "creditCard": {
            "default": "L\xfctfen ge\xe7erli bir kredi kart\u0131 numaras\u0131 giriniz"
        }
        , "cusip": {
            "default": "L\xfctfen ge\xe7erli bir CUSIP numaras\u0131 giriniz"
        }
        , "cvv": {
            "default": "L\xfctfen ge\xe7erli bir CCV numaras\u0131 giriniz"
        }
        , "date": {
            "default": "L\xfctfen ge\xe7erli bir tarih giriniz"
        }
        , "different": {
            "default": "L\xfctfen farkl\u0131 bir de\u011fer giriniz"
        }
        , "digits": {
            "default": "L\xfctfen sadece say\u0131 giriniz"
        }
        , "ean": {
            "default": "L\xfctfen ge\xe7erli bir EAN numaras\u0131 giriniz"
        }
        , "emailAddress": {
            "default": "L\xfctfen ge\xe7erli bir E-Mail adresi giriniz"
        }
        , "file": {
            "default": "L\xfctfen ge\xe7erli bir dosya se\xe7iniz"
        }
        , "greaterThan": {
            "default": "L\xfctfen %s ye e\u015fit veya daha b\xfcy\xfck bir de\u011fer giriniz", "notInclusive": "L\xfctfen %s den b\xfcy\xfck bir de\u011fer giriniz"
        }
        , "grid": {
            "default": "L\xfctfen ge\xe7erli bir GRId numaras\u0131 giriniz"
        }
        , "hex": {
            "default": "L\xfctfen ge\xe7erli bir Hexadecimal say\u0131 giriniz"
        }
        , "hexColor": {
            "default": "L\xfctfen ge\xe7erli bir HEX codu giriniz"
        }
        , "iban": {
            "default":"L\xfctfen ge\xe7erli bir IBAN numaras\u0131 giriniz", "countryNotSupported":"%s \xfclke kodu desteklenmemektedir", "country":"%s i\xe7ine l\xfctfen ge\xe7erli bir IBAN numaras\u0131 giriniz", "countries": {
                "AD": "Andorra", "AE": "Birle\u015fik Arap Emirlikleri", "AL": "Arnavutluk", "AO": "Angora", "AT": "Avusturya", "AZ": "Azerbaycan", "BA": "Bosna Hersek", "BE": "Bel\xe7ika", "BF": "Burkina Faso", "BG": "Bulgaristan", "BH": "Bahreyn", "BI": "Burundi", "BJ": "Benin", "BR": "Brezilya", "CH": "\u0130svi\xe7re", "CI": "Fildi\u015fi Sahili", "CM": "Kamerun", "CR": "Kosta Rika", "CV": "Cape Verde", "CY": "K\u0131br\u0131s", "CZ": "\xc7ek Cumhuriyeti", "DE": "Almanya", "DK": "Danimarka", "DO": "Dominik Cumhuriyeti", "DZ": "Cezayir", "EE": "Estonya", "ES": "\u0130spanya", "FI": "Finlandiya", "FO": "Faroe Adalar\u0131", "FR": "Fransa", "GB": "\u0130ngiltere", "GE": "Georgia", "GI": "Cebelitar\u0131k", "GL": "Gr\xf6nland", "GR": "Yunansitan", "GT": "Guatemala", "HR": "H\u0131rvatistan", "HU": "Macaristan", "IE": "\u0130rlanda", "IL": "\u0130srail", "IR": "\u0130ran", "IS": "\u0130zlanda", "IT": "\u0130talya", "JO": "\xdcrd\xfcn", "KW": "Kuveit", "KZ": "Kazakistan", "LB": "L\xfcbnan", "LI": "Lihten\u015ftayn", "LT": "Litvanya", "LU": "L\xfcksemburg", "LV": "Letonya", "MC": "Monako", "MD": "Moldova", "ME": "Karada\u011f", "MG": "Madagaskar", "MK": "Makedonya", "ML": "Mali", "MR": "Moritanya", "MT": "Malta", "MU": "Mauritius", "MZ": "Mozambik", "NL": "Hollanda", "NO": "Norve\xe7", "PK": "Pakistan", "PL": "Polanya", "PS": "Filistin", "PT": "Portekiz", "QA": "Katar", "RO": "Romanya", "RS": "Serbistan", "SA": "Suudi Arabistan", "SE": "\u0130sve\xe7", "SI": "Slovenya", "SK": "Slovakya", "SM": "San Marino", "SN": "Senegal", "TN": "Tunus", "TR": "Turkiye", "VG": "Virgin Adalar\u0131, \u0130ngiliz"
            }
        }
        , "id": {
            "default":"L\xfctfen ge\xe7erli bir tan\u0131mlama numaras\u0131 giriniz", "countryNotSupported":"%s \xfclke kodu desteklenmiyor", "country":"L\xfctfen ge\xe7erli bir %s kodu giriniz", "countries": {
                "BA": "Bosna Hersekli", "BG": "Bulgaristanl\u0131", "BR": "Brezilyal\u0131", "CH": "\u0130sve\xe7li", "CL": "\u015eilili", "CZ": "\xc7ek", "DK": "Danimarkal\u0131", "EE": "Estonyal\u0131", "ES": "\u0130spanyol", "FI": "Fin", "HR": "H\u0131rvat", "IE": "\u0130rlandal\u0131", "IS": "\u0130zlandal\u0131", "LT": "Litvanyal\u0131", "LV": "Latenyal\u0131", "ME": "Karada\u011fl\u0131", "MK": "Makedonyal\u0131", "NL": "Hollandal\u0131", "RO": "Roman", "RS": "S\u0131rp", "SE": "\u0130sve\xe7li", "SI": "Slovenyal\u0131", "SK": "Slovak", "SM": "San Marinolu", "ZA": "Kuzey Afrikal\u0131"
            }
        }
        , "identical": {
            "default": "L\xfctfen ayn\u0131 de\u011feri giriniz"
        }
        , "imei": {
            "default": "L\xfctfen ge\xe7erli bir IMEI numaras\u0131 giriniz"
        }
        , "imo": {
            "default": "L\xfctfen ge\xe7erli bir IMO numaras\u0131 giriniz"
        }
        , "integer": {
            "default": "L\xfctfen ge\xe7erli bir numara giriniz"
        }
        , "ip": {
            "default": "L\xfctfen ge\xe7erli bir IP adresi giriniz", "ipv4": "L\xfctfen ge\xe7erli bir IPv4 adresi giriniz", "ipv6": "L\xfctfen ge\xe7erli bri IPv6 adresi giriniz"
        }
        , "isbn": {
            "default": "L\xfctfen ge\xe7erli bir ISBN numaras\u0131 giriniz"
        }
        , "isin": {
            "default": "L\xfctfen ge\xe7erli bir ISIN numaras\u0131 giriniz"
        }
        , "ismn": {
            "default": "L\xfctfen ge\xe7erli bir ISMN numaras\u0131 giriniz"
        }
        , "issn": {
            "default": "L\xfctfen ge\xe7erli bir ISSN numaras\u0131 giriniz"
        }
        , "lessThan": {
            "default": "L\xfctfen %s den d\xfc\u015f\xfck veya e\u015fit bir de\u011fer giriniz", "notInclusive": "L\xfctfen %s den b\xfcy\xfck bir de\u011fer giriniz"
        }
        , "mac": {
            "default": "L\xfctfen ge\xe7erli bir MAC Adresi giriniz"
        }
        , "meid": {
            "default": "L\xfctfen ge\xe7erli bir MEID numaras\u0131 giriniz"
        }
        , "notEmpty": {
            "default": "Bir de\u011fer giriniz"
        }
        , "numeric": {
            "default": "L\xfctfen ge\xe7erli bir float de\u011fer giriniz"
        }
        , "phone": {
            "default":"L\xfctfen ge\xe7erli bir telefon numaras\u0131 giriniz", "countryNotSupported":"%s \xfclke kodu desteklenmemektedir", "country":"%s de ge\xe7erli bir telefon numaras\u0131 giriniz", "countries": {
                "BR": "Brezilya", "ES": "\u0130spanya", "FR": "Fransa", "GB": "\u0130ngiltere", "MA": "Fas", "PK": "Pakistan", "US": "Amerika"
            }
        }
        , "regexp": {
            "default": "L\xfctfen uyumlu bir de\u011fer giriniz"
        }
        , "remote": {
            "default": "L\xfctfen ge\xe7erli bir numara giriniz"
        }
        , "rtn": {
            "default": "L\xfctfen ge\xe7erli bir RTN numaras\u0131 giriniz"
        }
        , "sedol": {
            "default": "L\xfctfen ge\xe7erli bir SEDOL numaras\u0131 giriniz"
        }
        , "siren": {
            "default": "L\xfctfen ge\xe7erli bir SIREN numaras\u0131 giriniz"
        }
        , "siret": {
            "default": "L\xfctfen ge\xe7erli bir SIRET numaras\u0131 giriniz"
        }
        , "step": {
            "default": "L\xfctfen ge\xe7erli bir %s ad\u0131m\u0131 giriniz"
        }
        , "stringCase": {
            "default": "L\xfctfen sadece k\xfc\xe7\xfck harf giriniz", "upper": "L\xfctfen sadece b\xfcy\xfck harf giriniz"
        }
        , "stringLength": {
            "default": "L\xfctfen ge\xe7erli uzunluktaki bir de\u011fer giriniz", "less": "L\xfctfen %s karakterden az de\u011fer giriniz", "more": "L\xfctfen %s karakterden fazla de\u011fer giriniz", "between": "L\xfctfen %s ile %s aras\u0131 uzunlukta bir de\u011fer giriniz"
        }
        , "uri": {
            "default": "L\xfctfen ge\xe7erli bir URL giriniz"
        }
        , "uuid": {
            "default": "L\xfctfen ge\xe7erli bir UUID numaras\u0131 giriniz", "version": "L\xfctfen ge\xe7erli bir UUID versiyon %s numaras\u0131 giriniz"
        }
        , "vat": {
            "default":"L\xfctfen ge\xe7erli bir VAT kodu giriniz", "countryNotSupported":"%s \xfclke kodu desteklenmiyor", "country":"L\xfctfen ge\xe7erli bir %s KDV kodu giriniz", "countries": {
                "AT": "Avustralyal\u0131", "BE": "Bel\xe7ikal\u0131", "BG": "Bulgar", "BR": "Brezilyal\u0131", "CH": "\u0130svi\xe7reli", "CY": "K\u0131br\u0131sl\u0131", "CZ": "\xc7ek", "DE": "Alman", "DK": "Danimarkal\u0131", "EE": "Estonyal\u0131", "ES": "\u0130spanyol", "FI": "Fince", "FR": "Frans\u0131z", "GB": "\u0130ngiliz", "GR": "Yunan", "EL": "Yunan", "HU": "Macar", "HR": "H\u0131rvat", "IE": "Irlandal\u0131", "IS": "\u0130zlanda", "IT": "Italyan", "LT": "Litvanyal\u0131", "LU": "L\xfcksemburglu", "LV": "Letonyal\u0131", "MT": "Maltal\u0131", "NL": "Hollandal\u0131", "NO": "Norve\xe7li", "PL": "Polonyal\u0131", "PT": "Portekizli", "RO": "Romen", "RU": "Rus", "RS": "S\u0131rp", "SE": "\u0130sve\xe7", "SI": "Sloven", "SK": "Slovak", "ZA": "Kuzey Afrikal\u0131"
            }
        }
        , "vin": {
            "default": "L\xfctfen ge\xe7erli bir VIN numaras\u0131 giriniz"
        }
        , "zipCode": {
            "default":"L\xfctfen ge\xe7erli bir ZIP kodu giriniz", "countryNotSupported":"%s \xfclke kodu desteklenmemektedir", "country":"L\xfctfen ge\xe7erli bir %s", "countries": {
                "BR": "Brezilyal\u0131 posta kodu", "CA": "Kanada posta kodu", "DK": "Danimarka posta kodu", "GB": "\u0130ngiltere posta kodu", "IT": "\u0130talya posta kodu", "MA": "Fas posta kodu", "NL": "Almanya posta kodu", "SE": "\u0130sve\xe7 posta kodu", "SG": "Singapur posta kodu", "US": "Amerika posta kodu"
            }
        }
    }
    )
}

(window.jQuery);