/*! SmartAdmin - v1.5 - 2014-09-27 */

!function(a) {
    a.fn.bootstrapValidator.i18n=a.extend(!0, a.fn.bootstrapValidator.i18n, {
        "base64": {
            "default": "Por favor insira um c\xf3digo base 64 v\xe1lido"
        }
        , "between": {
            "default": "Por favor insira um valor entre %s e %s", "notInclusive": "Por favor insira um valor estritamente entre %s e %s"
        }
        , "callback": {
            "default": "Por favor insira um valor v\xe1lido"
        }
        , "choice": {
            "default": "Por favor insira um valor v\xe1lido", "less": "Por favor escolha %s op\xe7\xf5es no m\xednimo", "more": "Por favor escolha %s op\xe7\xf5es no m\xe1ximo", "between": "Por favor escolha %s a %s op\xe7\xf5es"
        }
        , "creditCard": {
            "default": "Por favor insira um n\xfamero de cart\xe3o de cr\xe9dito v\xe1lido"
        }
        , "cusip": {
            "default": "Por favor insira um n\xfamero CUSIP v\xe1lido"
        }
        , "cvv": {
            "default": "Por favor insira um n\xfamero CVV v\xe1lido"
        }
        , "date": {
            "default": "Por favor insira uma data v\xe1lida"
        }
        , "different": {
            "default": "Por favor insira valores diferentes"
        }
        , "digits": {
            "default": "Por favor insira somente d\xedgitos"
        }
        , "ean": {
            "default": "Por favor insira um n\xfamero EAN v\xe1lido"
        }
        , "emailAddress": {
            "default": "Por favor insira um email v\xe1lido"
        }
        , "file": {
            "default": "Por favor escolha um arquivo v\xe1lido"
        }
        , "greaterThan": {
            "default": "Por favor insira um valor maior ou igual a %s", "notInclusive": "Por favor insira um valor maior do que %s"
        }
        , "grid": {
            "default": "Por favor insira uma GRID v\xe1lida"
        }
        , "hex": {
            "default": "Por favor insira um hexadecimal v\xe1lido"
        }
        , "hexColor": {
            "default": "Por favor insira uma cor hexadecimal v\xe1lida"
        }
        , "iban": {
            "default":"Por favor insira um n\xfamero IBAN v\xe1lido", "countryNotSupported":"O c\xf3digo do pa\xeds %s n\xe3o \xe9 suportado", "country":"Por favor insira um n\xfamero IBAN v\xe1lido em %s", "countries": {
                "AD": "Andorra", "AE": "Emirados \xc1rabes", "AL": "Alb\xe2nia", "AO": "Angola", "AT": "\xc1ustria", "AZ": "Azerbaij\xe3o", "BA": "B\xf3snia-Herzegovina", "BE": "B\xe9lgica", "BF": "Burkina Faso", "BG": "Bulg\xe1ria", "BH": "Bahrain", "BI": "Burundi", "BJ": "Benin", "BR": "Brasil", "CH": "Su\xed\xe7a", "IC": "Costa do Marfim", "CM": "Camar\xf5es", "CR": "Costa Rica", "CV": "Cabo Verde", "CY": "Chipre", "CZ": "Rep\xfablica Checa", "DE": "Alemanha", "DK": "Dinamarca", "DO": "Rep\xfablica Dominicana", "DZ": "Arg\xe9lia", "EE": "Est\xf3nia", "ES": "Espanha", "FI": "Finl\xe2ndia", "FO": "Ilhas Faro\xe9", "FR": "Fran\xe7a", "GB": "Reino Unido", "GE": "Georgia", "GI": "Gibraltar", "GL": "Groenl\xe2ndia", "GR": "Gr\xe9cia", "GT": "Guatemala", "HR": "Cro\xe1cia", "HU": "Hungria", "IE": "Ireland", "IL": "Israel", "IR": "Ir\xe3o", "IS": "Isl\xe2ndia", "TI": "It\xe1lia", "JO": "Jordan", "KW": "Kuwait", "KZ": "Cazaquist\xe3o", "LB": "L\xedbano", "LI": "Liechtenstein", "LT": "Litu\xe2nia", "LU": "Luxemburgo", "LV": "Let\xf3nia", "MC": "M\xf4naco", "MD": "Mold\xe1via", "ME": "Montenegro", "MG": "Madagascar", "MK": "Maced\xf3nia", "ML": "Mali", "MR": "Maurit\xe2nia", "MT": "Malta", "MU": "Maur\xedcio", "MZ": "Mo\xe7ambique", "NL": "Pa\xedses Baixos", "NO": "Noruega", "PK": "Paquist\xe3o", "PL": "Pol\xf4nia", "PS": "Palestino", "PT": "Portugal", "QA": "Qatar", "RO": "Rom\xe9nia", "RS": "S\xe9rvia", "SA": "Ar\xe1bia Saudita", "SE": "Su\xe9cia", "SI": "Eslov\xe9nia", "SK": "Eslov\xe1quia", "SM": "San Marino", "SN": "Senegal", "TN": "Tun\xedsia", "TR": "Turquia", "VG": "Ilhas Virgens Brit\xe2nicas"
            }
        }
        , "id": {
            "default":"Por favor insir aum c\xf3digo de identifica\xe7\xe3o v\xe1lido", "countryNotSupported":"O c\xf3digo do pa\xeds %s n\xe3o \xe9 suportado", "country":"Por favor insira um npumero de indentifica\xe7\xe3o %s v\xe1lido", "countries": {
                "BA": "B\xf3snia-Herzegovina", "BG": "B\xfalgaro", "BR": "Brasileiro", "CH": "Su\xed\xe7a", "CL": "Chileno", "CZ": "Checa", "DK": "Dinamarqu\xeas", "EE": "Estonian", "ES": "Espanhol", "FI": "Finland\xeas", "HR": "Croata", "IE": "Irland\xeas", "IS": "Isl\xe2ndia", "LT": "Litu\xe2nia", "LV": "Let\xe3o", "ME": "Montenegro", "MK": "Maced\xf4nio", "NL": "Holand\xeas", "RO": "Romeno", "RS": "S\xe9rvia", "SE": "Sueco", "SI": "Esloveno", "SK": "Eslovaca", "SM": "San Marino", "ZA": "Sul-Africano"
            }
        }
        , "identical": {
            "default": "Por favor, insira o mesmo valor"
        }
        , "imei": {
            "default": "Por favor insira um IMEI v\xe1lido"
        }
        , "imo": {
            "default": "Por favor insira um IMO v\xe1lido"
        }
        , "integer": {
            "default": "Por favor insira um n\xfamero inteiro v\xe1lido"
        }
        , "ip": {
            "default": "Por favor insira um IP v\xe1lido", "ipv4": "Por favor insira um endere\xe7o de IPv4 v\xe1lido", "ipv6": "Por favor insira um endere\xe7o de IPv6 v\xe1lido"
        }
        , "isbn": {
            "default": "Por favor insira um ISBN v\xe1lido"
        }
        , "isin": {
            "default": "Por favor insira um ISIN v\xe1lido"
        }
        , "ismn": {
            "default": "Por favor insira um ISMN v\xe1lido"
        }
        , "issn": {
            "default": "Por favor insira um ISSN v\xe1lido"
        }
        , "lessThan": {
            "default": "Por favor insira um valor menor ou igual a %s", "notInclusive": "Por favor insira um valor menor do que %s"
        }
        , "mac": {
            "default": "Por favor insira um endere\xe7o MAC v\xe1lido"
        }
        , "meid": {
            "default": "Por favor insira um MEID v\xe1lido"
        }
        , "notEmpty": {
            "default": "Por favor insira um valor"
        }
        , "numeric": {
            "default": "Por favor insira um n\xfamero real v\xe1lido"
        }
        , "phone": {
            "default":"Por favor insira um n\xfamero de telefone v\xe1lido", "countryNotSupported":"O c\xf3digo de pa\xeds %s n\xe3o \xe9 suportado", "country":"Por favor insira um n\xfamero de telefone v\xe1lido %s", "countries": {
                "BR": "Brasil", "ES": "Espanha", "FR": "Fran\xe7a", "GB": "Reino Unido", "MA": "Marrocos", "PK": "Paquist\xe3o", "US": "Estados Unidos"
            }
        }
        , "regexp": {
            "default": "Por favor insira um valor correspondente ao padr\xe3o"
        }
        , "remote": {
            "default": "Por favor insira um valor v\xe1lido"
        }
        , "rtn": {
            "default": "Por favor insira um n\xfamero v\xe1lido RTN"
        }
        , "sedol": {
            "default": "Por favor insira um n\xfamero v\xe1lido SEDOL"
        }
        , "siren": {
            "default": "Por favor insira um n\xfamero v\xe1lido SIREN"
        }
        , "siret": {
            "default": "Por favor insira um n\xfamero v\xe1lido SIRET"
        }
        , "step": {
            "default": "Por favor insira um passo v\xe1lido %s"
        }
        , "stringCase": {
            "default": "Por favor, digite apenas caracteres min\xfasculos", "upper": "Por favor, digite apenas caracteres mai\xfasculos"
        }
        , "stringLength": {
            "default": "Por favor insira um valor com comprimento v\xe1lido", "less": "Por favor insira menos de %s caracteres", "more": "Por favor insira mais de %s caracteres", "between": "Por favor insira um valor entre %s e %s caracteres"
        }
        , "uri": {
            "default": "Por favor insira um URI v\xe1lido"
        }
        , "uuid": {
            "default": "Por favor insira um n\xfamero v\xe1lido UUID", "version": "Por favor insira uma vers\xe3o %s  UUID v\xe1lida"
        }
        , "vat": {
            "default":"Por favor insira um VAT v\xe1lido", "countryNotSupported":"O c\xf3digo do pa\xeds %s n\xe3o \xe9 suportado", "country":"Por favor insira um n\xfamero %s VAT v\xe1lido", "countries": {
                "AT": "Austr\xedaco", "BE": "Belga", "BG": "B\xfalgaro", "BR": "Brasileiro", "CH": "Su\xed\xe7a", "CY": "cipriota", "CZ": "Checa", "DE": "Alem\xe3o", "DK": "Dinamarqu\xeas", "EE": "Estonian", "ES": "Espanhol", "FI": "Finland\xeas", "FR": "Franc\xeas", "GB": "Reino Unido", "GR": "Grego", "EL": "Grego", "HU": "H\xfangaro", "HR": "Croata", "IE": "irland\xeas", "IS": "Isl\xe2ndia", "IT": "Italiano", "LT": "Litu\xe2nia", "LU": "Luxemburgo", "LV": "Let\xe3o", "MT": "Maltese", "NL": "Holand\xeas", "NO": "Noruegu\xeas", "PL": "Polaco", "PT": "Portugu\xeas", "RO": "Romeno", "RU": "Russo", "RS": "S\xe9rvia", "SE": "Sueco", "SI": "esloveno", "SK": "Eslovaca", "ZA": "Sul-Africano"
            }
        }
        , "vin": {
            "default": "Por favor insira um VIN v\xe1lido"
        }
        , "zipCode": {
            "default":"Por favor insira um CEP v\xe1lido", "countryNotSupported":"O c\xf3digo postal do pa\xeds %s n\xe3o \xe9 suportado", "country":"Por favor insira um c\xf3digo postal %s v\xe1lido", "countries": {
                "BR": "C\xf3digo postal Brasileiro", "CA": "C\xf3digo postal Canadense", "DK": "C\xf3digo postal Dinamarqu\xeas", "GB": "C\xf3digo postal do Reino Unido", "IT": "C\xf3digo postal Italiano", "MA": "C\xf3digo postal Marroquino", "NL": "C\xf3digo postal Holand\xeas", "SE": "C\xf3digo postal Su\xed\xe7o", "SG": "C\xf3digo postal Cingapura", "US": "C\xf3digo postal dos EUA"
            }
        }
    }
    )
}

(window.jQuery);