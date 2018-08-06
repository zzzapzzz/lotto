/*! SmartAdmin - v1.5 - 2014-09-27 */

!function(a) {
    a.fn.bootstrapValidator.i18n=a.extend(!0, a.fn.bootstrapValidator.i18n, {
        "base64": {
            "default": "Por favor ingrese un valor v\xe1lido en base 64"
        }
        , "between": {
            "default": "Por favor ingrese un valor entre %s y %s", "notInclusive": "Por favor ingrese un valor s\xf3lo entre %s and %s"
        }
        , "callback": {
            "default": "Por favor ingrese un valor v\xe1lido"
        }
        , "choice": {
            "default": "Por favor ingrese un valor v\xe1lido", "less": "Por favor elija %s opciones como m\xednimo", "more": "Por favor elija %s optiones como m\xe1ximo", "between": "Por favor elija de %s a %s opciones"
        }
        , "creditCard": {
            "default": "Por favor ingrese un n\xfamero v\xe1lido de tarjeta de cr\xe9dito"
        }
        , "cusip": {
            "default": "Por favor ingrese un n\xfamero CUSIP v\xe1lido"
        }
        , "cvv": {
            "default": "Por favor ingrese un n\xfamero CVV v\xe1lido"
        }
        , "date": {
            "default": "Por favor ingrese una fecha v\xe1lida"
        }
        , "different": {
            "default": "Por favor ingrese un valor distinto"
        }
        , "digits": {
            "default": "Por favor ingrese s\xf3lo d\xedgitos"
        }
        , "ean": {
            "default": "Por favor ingrese un n\xfamero EAN v\xe1lido"
        }
        , "emailAddress": {
            "default": "Por favor ingrese un email v\xe1lido"
        }
        , "file": {
            "default": "Por favor elija un archivo v\xe1lido"
        }
        , "greaterThan": {
            "default": "Por favor ingrese un valor mayor o igual a %s", "notInclusive": "Por favor ingrese un valor mayor que %s"
        }
        , "grid": {
            "default": "Por favor ingrese un n\xfamero GRId v\xe1lido"
        }
        , "hex": {
            "default": "Por favor ingrese un valor hexadecimal v\xe1lido"
        }
        , "hexColor": {
            "default": "Por favor ingrese un color hexadecimal v\xe1lido"
        }
        , "iban": {
            "default":"Por favor ingrese un n\xfamero IBAN v\xe1lido", "countryNotSupported":"El c\xf3digo del pa\xeds %s no est\xe1 soportado", "country":"Por favor ingrese un n\xfamero IBAN v\xe1lido en %s", "countries": {
                "AD": "Andorra", "AE": "Emiratos \xc1rabes Unidos", "AL": "Albania", "AO": "Angola", "AT": "Austria", "AZ": "Azerbaiy\xe1n", "BA": "Bosnia-Herzegovina", "BE": "B\xe9lgica", "BF": "Burkina Faso", "BG": "Bulgaria", "BH": "Bar\xe9in", "BI": "Burundi", "BJ": "Ben\xedn", "BR": "Brasil", "CH": "Suiza", "CI": "Costa de Marfil", "CM": "Camer\xfan", "CR": "Costa Rica", "CV": "Cabo Verde", "CY": "Cyprus", "CZ": "Rep\xfablica Checa", "DE": "Alemania", "DK": "Dinamarca", "DO": "Rep\xfablica Dominicana", "DZ": "Argelia", "EE": "Estonia", "ES": "Espa\xf1a", "FI": "Finlandia", "FO": "Islas Feroe", "FR": "Francia", "GB": "Reino Unido", "GE": "Georgia", "GI": "Gibraltar", "GL": "Groenlandia", "GR": "Grecia", "GT": "Guatemala", "HR": "Croacia", "HU": "Hungr\xeda", "IE": "Irlanda", "IL": "Israel", "IR": "Iran", "IS": "Islandia", "IT": "Italia", "JO": "Jordania", "KW": "Kuwait", "KZ": "Kazajist\xe1n", "LB": "L\xedbano", "LI": "Liechtenstein", "LT": "Lituania", "LU": "Luxemburgo", "LV": "Letonia", "MC": "M\xf3naco", "MD": "Moldavia", "ME": "Montenegro", "MG": "Madagascar", "MK": "Macedonia", "ML": "Mal\xed", "MR": "Mauritania", "MT": "Malta", "MU": "Mauricio", "MZ": "Mozambique", "NL": "Pa\xedses Bajos", "NO": "Noruega", "PK": "Pakist\xe1n", "PL": "Poland", "PS": "Palestina", "PT": "Portugal", "QA": "Catar", "RO": "Rumania", "RS": "Serbia", "SA": "Arabia Saudita", "SE": "Suecia", "SI": "Eslovenia", "SK": "Eslovaquia", "SM": "San Marino", "SN": "Senegal", "TN": "T\xfanez", "TR": "Turqu\xeda", "VG": "Islas V\xedrgenes Brit\xe1nicas"
            }
        }
        , "id": {
            "default":"Por favor ingrese un n\xfamero de identificaci\xf3n v\xe1lido", "countryNotSupported":"El c\xf3digo del pa\xeds %s no esta soportado", "country":"Por favor ingrese un n\xfamero de identificaci\xf3n %s v\xe1lido", "countries": {
                "BA": "Bosnioherzegovino", "BG": "B\xfalgaro", "BR": "Brasile\xf1o", "CH": "Suizo", "CL": "Chileno", "CZ": "Checo", "DK": "Dan\xe9s", "EE": "Estonio", "ES": "Espa\xf1ol", "FI": "Finland\xe9s", "HR": "Croata", "IE": "Irland\xe9s", "IS": "Island\xe9s", "LT": "Lituano", "LV": "Let\xf3n", "ME": "Montenegrino", "MK": "Macedonio", "NL": "Alem\xe1n", "RO": "Romano", "RS": "Serbio", "SE": "Sueco", "SI": "Esloveno", "SK": "Eslovaco", "SM": "Sanmarinense", "ZA": "Sudafricano"
            }
        }
        , "identical": {
            "default": "Por favor ingrese el mismo valor"
        }
        , "imei": {
            "default": "Por favor ingrese un n\xfamero IMEI v\xe1lido"
        }
        , "imo": {
            "default": "Por favor ingrese un n\xfamero IMO v\xe1lido"
        }
        , "integer": {
            "default": "Por favor ingrese un n\xfamero v\xe1lido"
        }
        , "ip": {
            "default": "Por favor ingrese una direcci\xf3n IP v\xe1lida", "ipv4": "Por favor ingrese una direcci\xf3n IPv4 v\xe1lida", "ipv6": "Por favor ingrese una direcci\xf3n IPv6 v\xe1lida"
        }
        , "isbn": {
            "default": "Por favor ingrese un n\xfamero ISBN v\xe1lido"
        }
        , "isin": {
            "default": "Por favor ingrese un n\xfamero ISIN v\xe1lido"
        }
        , "ismn": {
            "default": "Por favor ingrese un n\xfamero ISMN v\xe1lido"
        }
        , "issn": {
            "default": "Por favor ingrese un n\xfamero ISSN v\xe1lido"
        }
        , "lessThan": {
            "default": "Por favor ingrese un valor menor o igual a %s", "notInclusive": "Por favor ingrese un valor menor que %s"
        }
        , "mac": {
            "default": "Por favor ingrese una direcci\xf3n MAC v\xe1lida"
        }
        , "meid": {
            "default": "Por favor ingrese un n\xfamero MEID v\xe1lido"
        }
        , "notEmpty": {
            "default": "Por favor ingrese un valor"
        }
        , "numeric": {
            "default": "Por favor ingrese un n\xfamero decimal v\xe1lido"
        }
        , "phone": {
            "default":"Por favor ingrese un n\xfamero v\xe1lido de tel\xe9fono", "countryNotSupported":"El c\xf3digo del pa\xeds %s no est\xe1 soportado", "country":"Por favor ingrese un n\xfamero v\xe1lido de tel\xe9fono en %s", "countries": {
                "BR": "Brasil", "ES": "Espa\xf1a", "FR": "Francia", "GB": "Reino Unido", "MA": "Marruecos", "PK": "Pakist\xe1n", "US": "EE.UU."
            }
        }
        , "regexp": {
            "default": "Por favor ingrese un valor que coincida con el patr\xf3n"
        }
        , "remote": {
            "default": "Por favor ingrese un valor v\xe1lido"
        }
        , "rtn": {
            "default": "Por favor ingrese un n\xfamero RTN v\xe1lido"
        }
        , "sedol": {
            "default": "Por favor ingrese un n\xfamero SEDOL v\xe1lido"
        }
        , "siren": {
            "default": "Por favor ingrese un n\xfamero SIREN v\xe1lido"
        }
        , "siret": {
            "default": "Por favor ingrese un n\xfamero SIRET v\xe1lido"
        }
        , "step": {
            "default": "Por favor ingrese un paso v\xe1lido de %s"
        }
        , "stringCase": {
            "default": "Por favor ingrese s\xf3lo caracteres en min\xfascula", "upper": "Por favor ingrese s\xf3lo caracteres en may\xfascula"
        }
        , "stringLength": {
            "default": "Por favor ingrese un valor con una longitud v\xe1lida", "less": "Por favor ingrese menos de %s caracteres", "more": "Por favor ingrese m\xe1s de %s caracteres", "between": "Por favor ingrese un valor con una longitud entre %s y %s caracteres"
        }
        , "uri": {
            "default": "Por favor ingresese una URI v\xe1lida"
        }
        , "uuid": {
            "default": "Por favor ingrese un n\xfamero UUID v\xe1lido", "version": "Por favor ingrese una versi\xf3n UUID v\xe1lida para %s"
        }
        , "vat": {
            "default":"Por favor ingrese un n\xfamero VAT v\xe1lido", "countryNotSupported":"El c\xf3digo del pa\xeds %s no est\xe1 soportado", "country":"Por favor ingrese un n\xfamero VAT %s v\xe1lido", "countries": {
                "AT": "Austriaco", "BE": "Belga", "BG": "B\xfalgaro", "BR": "Brasile\xf1o", "CH": "Suizo", "CY": "Chipriota", "CZ": "Checo", "DE": "Alem\xe1n", "DK": "Dan\xe9s", "EE": "Estonio", "ES": "Espa\xf1ol", "FI": "Finland\xe9s", "FR": "Franc\xe9s", "GB": "Brit\xe1nico", "GR": "Griego", "EL": "Griego", "HU": "H\xfangaro", "HR": "Croata", "IE": "Irland\xe9s", "IS": "Islandia", "IT": "Italiano", "LT": "Lituano", "LU": "Luxemburgu\xe9s", "LV": "Let\xf3n", "MT": "Malt\xe9s", "NL": "Holand\xe9s", "NO": "Noruego", "PL": "Polaco", "PT": "Portugu\xe9s", "RO": "Rumano", "RU": "Ruso", "RS": "Serbio", "SE": "Sueco", "SI": "Esloveno", "SK": "Eslovaco", "ZA": "Sudafricano"
            }
        }
        , "vin": {
            "default": "Por favor ingrese un n\xfamero VIN v\xe1lido"
        }
        , "zipCode": {
            "default":"Por favor ingrese un c\xf3digo postal v\xe1lido", "countryNotSupported":"El c\xf3digo del pa\xeds %s no est\xe1 soportado", "country":"Por favor ingrese un %s v\xe1lido", "countries": {
                "BR": "c\xf3digo postal Brasile\xf1o", "CA": "c\xf3digo postal Canadiense", "DK": "c\xf3digo postal Dan\xe9s", "GB": "c\xf3digo postal Brit\xe1nico", "IT": "c\xf3digo postal Italiano", "MA": "c\xf3digo postal Marruecos", "NL": "c\xf3digo postal Holand\xe9s", "SE": "c\xf3digo postal Suizo", "SG": "c\xf3digo postal Singapurense", "US": "c\xf3digo postal de EE.UU."
            }
        }
    }
    )
}

(window.jQuery);