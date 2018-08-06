/*! SmartAdmin - v1.5 - 2014-09-27 */

!function(a) {
    a.fn.bootstrapValidator.i18n=a.extend(!0, a.fn.bootstrapValidator.i18n, {
        "base64": {
            "default": "\u8acb\u8f38\u5165\u6709\u6548\u7684Base64\u7de8\u78bc"
        }
        , "between": {
            "default": "\u8acb\u8f38\u5165\u4e0d\u5c0f\u65bc %s \u4e14\u4e0d\u5927\u65bc %s \u7684\u503c", "notInclusive": "\u8acb\u8f38\u5165\u4e0d\u5c0f\u65bc\u7b49\u65bc %s \u4e14\u4e0d\u5927\u65bc\u7b49\u65bc %s \u7684\u503c"
        }
        , "callback": {
            "default": "\u8acb\u8f38\u5165\u6709\u6548\u7684\u503c"
        }
        , "choice": {
            "default": "\u8acb\u8f38\u5165\u6709\u6548\u7684\u503c", "less": "\u6700\u5c11\u9078\u64c7 %s \u500b\u9078\u9805", "more": "\u6700\u591a\u9078\u64c7 %s \u500b\u9078\u9805", "between": "\u8acb\u9078\u64c7 %s \u81f3 %s \u500b\u9078\u9805"
        }
        , "creditCard": {
            "default": "\u8acb\u8f38\u5165\u6709\u6548\u7684\u4fe1\u7528\u5361\u865f\u78bc"
        }
        , "cusip": {
            "default": "\u8acb\u8f38\u5165\u6709\u6548\u7684CUSIP"
        }
        , "cvv": {
            "default": "\u8acb\u8f38\u5165\u6709\u6548\u7684CVV"
        }
        , "date": {
            "default": "\u8acb\u8f38\u5165\u6709\u6548\u7684\u65e5\u671f"
        }
        , "different": {
            "default": "\u8acb\u8f38\u5165\u4e0d\u4e00\u6a23\u7684\u503c"
        }
        , "digits": {
            "default": "\u53ea\u80fd\u8f38\u5165\u6578\u5b57"
        }
        , "ean": {
            "default": "\u8acb\u8f38\u5165\u6709\u6548\u7684EAN"
        }
        , "emailAddress": {
            "default": "\u8acb\u8f38\u5165\u6709\u6548\u7684EMAIL"
        }
        , "file": {
            "default": "\u8acb\u9078\u64c7\u6709\u6548\u7684\u6a94\u6848"
        }
        , "greaterThan": {
            "default": "\u8acb\u8f38\u5165\u5927\u65bc\u6216\u7b49\u65bc %s \u7684\u503c", "notInclusive": "\u8acb\u8f38\u5165\u5927\u65bc %s \u7684\u503c"
        }
        , "grid": {
            "default": "\u8acb\u8f38\u5165\u6709\u6548\u7684GRId"
        }
        , "hex": {
            "default": "\u8acb\u8f38\u5165\u6709\u6548\u768416\u4f4d\u5143\u78bc"
        }
        , "hexColor": {
            "default": "\u8acb\u8f38\u5165\u6709\u6548\u768416\u4f4d\u8272\u78bc"
        }
        , "iban": {
            "default":"\u8acb\u8f38\u5165\u6709\u6548\u7684IBAN ", "countryNotSupported":"\u4e0d\u652f\u63f4\u8a72\u570b\u5bb6\u4ee3\u78bc %s", "country":"\u8acb\u8f38\u5165\u6709\u6548\u7684 %s IBAN\u7de8\u865f ", "countries": {
                "AD": "\u5b89\u9053\u200b\u200b\u723e", "AE": "\u963f\u806f\u914b", "AL": "\u963f\u723e\u5df4\u5c3c\u4e9e", "AO": "\u5b89\u54e5\u62c9", "AT": "\u5967\u5730\u5229", "AZ": "\u963f\u585e\u62dc\u7586", "BA": "\u6ce2\u65af\u5c3c\u4e9e\u548c\u9ed1\u585e\u54e5\u7dad\u90a3", "BE": "\u6bd4\u5229\u6642", "BF": "\u5e03\u57fa\u7d0d\u6cd5\u7d22", "BG": "\u4fdd\u52a0\u5229\u4e9e", "BH": "\u5df4\u6797", "BI": "\u5e03\u9686\u8fea", "BJ": "\u8c9d\u5be7", "BR": "\u5df4\u897f", "CH": "\u745e\u58eb", "CI": "\u8c61\u7259\u6d77\u5cb8", "CM": "\u5580\u9ea5\u9686", "CR": "\u54e5\u65af\u9054\u9ece\u52a0", "CV": "\u4f5b\u5f97\u89d2", "CY": "\u585e\u6d66\u8def\u65af", "CZ": "\u6377\u514b\u5171\u548c\u570b", "DE": "\u5fb7\u570b", "DK": "\u4e39\u9ea5", "DO": "\u591a\u660e\u5c3c\u52a0\u5171\u548c\u570b", "DZ": "\u963f\u723e\u53ca\u5229\u4e9e", "EE": "\u611b\u6c99\u5c3c\u4e9e", "ES": "\u897f\u73ed\u7259", "FI": "\u82ac\u862d", "FO": "\u6cd5\u7f85\u7fa4\u5cf6", "FR": "\u6cd5\u570b", "GB": "\u82f1\u570b", "GE": "\u683c\u9b6f\u5409\u4e9e", "GI": "\u76f4\u5e03\u7f85\u9640", "GL": "\u683c\u9675\u862d\u5cf6", "GR": "\u5e0c\u81d8", "GT": "\u5371\u5730\u99ac\u62c9", "HR": "\u514b\u7f85\u5730\u4e9e", "HU": "\u5308\u7259\u5229", "IE": "\u611b\u723e\u862d", "IL": "\u4ee5\u8272\u5217", "IR": "\u4f0a\u6717", "IS": "\u51b0\u5cf6", "IT": "\u610f\u5927\u5229", "JO": "\u55ac\u4e39", "KW": "\u79d1\u5a01\u7279", "KZ": "\u54c8\u85a9\u514b\u65af\u5766", "LB": "\u9ece\u5df4\u5ae9", "LI": "\u5217\u652f\u6566\u58eb\u767b", "LT": "\u7acb\u9676\u5b9b", "LU": "\u76e7\u68ee\u5821", "LV": "\u62c9\u812b\u7dad\u4e9e", "MC": "\u6469\u7d0d\u54e5", "MD": "\u6469\u723e\u591a\u74e6", "ME": "\u9ed1\u5c71\u5171\u548c\u570b", "MG": "\u99ac\u9054\u52a0\u65af\u52a0", "MK": "\u99ac\u5176\u9813", "ML": "\u99ac\u91cc", "MR": "\u6bdb\u91cc\u5854\u5c3c\u4e9e", "MT": "\u99ac\u8033\u4ed6", "MU": "\u6bdb\u91cc\u6c42\u65af", "MZ": "\u83ab\u6851\u6bd4\u514b", "NL": "\u8377\u862d", "NO": "\u632a\u5a01", "PK": "\u5df4\u57fa\u65af\u5766", "PL": "\u6ce2\u862d", "PS": "\u5df4\u52d2\u65af\u5766", "PT": "\u8461\u8404\u7259", "QA": "\u5361\u5854\u723e", "RO": "\u7f85\u99ac\u5c3c\u4e9e", "RS": "\u585e\u723e\u7dad\u4e9e", "SA": "\u6c99\u7279\u963f\u62c9\u4f2f", "SE": "\u745e\u5178", "SI": "\u65af\u6d1b\u6587\u5c3c\u4e9e", "SK": "\u65af\u6d1b\u4f10\u514b", "SM": "\u8056\u99ac\u529b\u8afe", "SN": "\u585e\u5167\u52a0\u723e", "TN": "\u7a81\u5c3c\u65af", "TR": "\u571f\u8033\u5176", "VG": "\u82f1\u5c6c\u7dad\u723e\u4eac\u7fa4\u5cf6"
            }
        }
        , "id": {
            "default":"\u8acb\u8f38\u5165\u6709\u6548\u7684\u8eab\u4efd\u8b49\u5b57\u865f\u78bc", "countryNotSupported":"\u4e0d\u652f\u63f4\u8a72\u570b\u5bb6\u4ee3\u78bc %s", "country":"\u8acb\u8f38\u5165\u6709\u6548\u7684 %s \u8eab\u4efd\u8b49\u5b57\u865f\u78bc", "countries": {
                "BA": "\u6ce2\u65af\u5c3c\u4e9e\u548c\u9ed1\u585e\u54e5\u7dad\u90a3", "BG": "\u4fdd\u52a0\u5229\u4e9e", "BR": "\u5df4\u897f", "CH": "\u745e\u58eb", "CL": "\u667a\u5229", "CZ": "\u6377\u514b", "DK": "\u4e39\u9ea5", "EE": "\u611b\u6c99\u5c3c\u4e9e", "ES": "\u897f\u73ed\u7259\u8a9e", "FI": "\u82ac\u862d", "HR": "\u514b\u7f85\u5730\u4e9e", "IE": "\u611b\u723e\u862d", "IS": "\u51b0\u5cf6", "LT": "\u7acb\u9676\u5b9b", "LV": "\u62c9\u812b\u7dad\u4e9e\u8a9e", "ME": "\u9ed1\u5c71\u5171\u548c\u570b", "MK": "\u99ac\u5176\u9813", "NL": "\u8377\u862d", "RO": "\u7f85\u99ac\u5c3c\u4e9e", "RS": "\u585e\u723e\u7dad\u4e9e", "SE": "\u745e\u5178", "SI": "\u65af\u6d1b\u6587\u5c3c\u4e9e", "SK": "\u65af\u6d1b\u4f10\u514b", "SM": "\u8056\u99ac\u529b\u8afe", "ZA": "\u5357\u975e"
            }
        }
        , "identical": {
            "default": "\u8acb\u8f38\u5165\u4e00\u6a23\u7684\u503c"
        }
        , "imei": {
            "default": "\u8acb\u8f38\u5165\u6709\u6548\u7684IMEI"
        }
        , "imo": {
            "default": "\u8acb\u8f38\u5165\u6709\u6548\u7684IMO"
        }
        , "integer": {
            "default": "\u8acb\u8f38\u5165\u6709\u6548\u7684\u6574\u6578"
        }
        , "ip": {
            "default": "\u8acb\u8f38\u5165\u6709\u6548\u7684IP\u4f4d\u5740", "ipv4": "\u8acb\u8f38\u5165\u6709\u6548\u7684IPv4\u4f4d\u5740", "ipv6": "\u8acb\u8f38\u5165\u6709\u6548\u7684IPv6\u4f4d\u5740"
        }
        , "isbn": {
            "default": "\u8acb\u8f38\u5165\u6709\u6548\u7684ISBN"
        }
        , "isin": {
            "default": "\u8acb\u8f38\u5165\u6709\u6548\u7684ISIN"
        }
        , "ismn": {
            "default": "\u8acb\u8f38\u5165\u6709\u6548\u7684ISMN"
        }
        , "issn": {
            "default": "\u8acb\u8f38\u5165\u6709\u6548\u7684ISSN"
        }
        , "lessThan": {
            "default": "\u8acb\u8f38\u5165\u5c0f\u65bc\u6216\u7b49\u65bc %s \u7684\u503c", "notInclusive": "\u8acb\u8f38\u5165\u5c0f\u65bc %s \u7684\u503c"
        }
        , "mac": {
            "default": "\u8acb\u8f38\u5165\u6709\u6548\u7684MAC\u4f4d\u5740"
        }
        , "meid": {
            "default": "\u8acb\u8f38\u5165\u6709\u6548\u7684MEID"
        }
        , "notEmpty": {
            "default": "\u6b04\u4f4d\u4e0d\u80fd\u70ba\u7a7a"
        }
        , "numeric": {
            "default": "\u8acb\u8f38\u5165\u6709\u6548\u7684\u6d6e\u9ede\u6578"
        }
        , "phone": {
            "default":"\u8acb\u8f38\u5165\u6709\u6548\u7684\u96fb\u8a71\u865f\u78bc", "countryNotSupported":"\u4e0d\u652f\u63f4\u8a72\u570b\u5bb6\u4ee3\u78bc %s", "country":"\u8acb\u8f38\u5165\u6709\u6548\u7684 %s \u96fb\u8a71\u865f\u78bc", "countries": {
                "BR": "\u5df4\u897f", "ES": "\u897f\u73ed\u7259", "FR": "\u6cd5\u570b", "GB": "\u82f1\u570b", "MA": "\u6469\u6d1b\u54e5", "PK": "\u5df4\u57fa\u65af\u5766", "US": "\u7f8e\u570b"
            }
        }
        , "regexp": {
            "default": "\u8acb\u8f38\u5165\u6709\u6548\u7684\u503c"
        }
        , "remote": {
            "default": "\u8acb\u8f38\u5165\u6709\u6548\u7684\u503c"
        }
        , "rtn": {
            "default": "\u8acb\u8f38\u5165\u6709\u6548\u7684RTN"
        }
        , "sedol": {
            "default": "\u8acb\u8f38\u5165\u6709\u6548\u7684SEDOL"
        }
        , "siren": {
            "default": "\u8acb\u8f38\u5165\u6709\u6548\u7684SIREN"
        }
        , "siret": {
            "default": "\u8acb\u8f38\u5165\u6709\u6548\u7684SIRET"
        }
        , "step": {
            "default": "\u8acb\u8f38\u5165 %s \u500b\u6709\u6548\u6b65\u9a5f"
        }
        , "stringCase": {
            "default": "\u53ea\u80fd\u8f38\u5165\u5c0f\u5beb\u7684\u503c", "upper": "\u53ea\u80fd\u8f38\u5165\u5927\u5beb\u7684\u503c"
        }
        , "stringLength": {
            "default": "\u8acb\u8f38\u5165\u7b26\u5408\u9577\u5ea6\u9650\u5236\u7684\u503c", "less": "\u8acb\u8f38\u5165\u5c0f\u65bc %s \u500b\u5b57", "more": "\u8acb\u8f38\u5165\u5927\u65bc %s \u500b\u5b57", "between": "\u8acb\u8f38\u5165\u4ecb\u65bc %s \u81f3 %s \u500b\u5b57"
        }
        , "uri": {
            "default": "\u8acb\u8f38\u5165\u4e00\u500b\u6709\u6548\u7684\u93c8\u63a5"
        }
        , "uuid": {
            "default": "\u8acb\u8f38\u5165\u6709\u6548\u7684UUID", "version": "\u8acb\u8f38\u5165\u7b26\u5408\u7248\u672c %s \u7684UUID"
        }
        , "vat": {
            "default":"\u8acb\u8f38\u5165\u6709\u6548\u7684VAT", "countryNotSupported":"\u4e0d\u652f\u63f4\u8a72\u570b\u5bb6\u4ee3\u78bc %s", "country":"\u8acb\u8f38\u5165\u6709\u6548\u7684 %s VAT", "countries": {
                "AT": "\u5967\u5730\u5229", "BE": "\u6bd4\u5229\u6642", "BG": "\u4fdd\u52a0\u5229\u4e9e", "BR": "\u5df4\u897f", "CH": "\u745e\u58eb", "CY": "\u585e\u6d66\u8def\u65af", "CZ": "\u6377\u514b", "DE": "\u5fb7\u8a9e", "DK": "\u4e39\u9ea5", "EE": "\u611b\u6c99\u5c3c\u4e9e", "ES": "\u897f\u73ed\u7259", "FI": "\u82ac\u862d", "FR": "\u6cd5\u8a9e", "GB": "\u82f1\u570b", "GR": "\u5e0c\u81d8", "EL": "\u5e0c\u81d8", "HU": "\u5308\u7259\u5229", "HR": "\u514b\u7f85\u5730\u4e9e", "IE": "\u611b\u723e\u862d", "IS": "\u51b0\u5cf6", "IT": "\u610f\u5927\u5229", "LT": "\u7acb\u9676\u5b9b", "LU": "\u76e7\u68ee\u5821", "LV": "\u62c9\u812b\u7dad\u4e9e\u8a9e", "MT": "\u99ac\u8033\u4ed6", "NL": "\u8377\u862d", "NO": "\u632a\u5a01", "PL": "\u6ce2\u862d", "PT": "\u8461\u8404\u7259", "RO": "\u7f85\u99ac\u5c3c\u4e9e", "RU": "\u4fc4\u7f85\u65af", "RS": "\u585e\u723e\u7dad\u4e9e", "SE": "\u745e\u5178", "SI": "\u65af\u6d1b\u6587\u5c3c\u4e9e", "SK": "\u65af\u6d1b\u4f10\u514b", "ZA": "\u5357\u975e"
            }
        }
        , "vin": {
            "default": "\u8acb\u8f38\u5165\u6709\u6548\u7684VIN"
        }
        , "zipCode": {
            "default":"\u8acb\u8f38\u5165\u6709\u6548\u7684\u90f5\u653f\u7de8\u78bc", "countryNotSupported":"\u4e0d\u652f\u63f4\u8a72\u570b\u5bb6\u4ee3\u78bc %s", "country":"\u8acb\u8f38\u5165\u6709\u6548\u7684 %s", "countries": {
                "BR": "\u5df4\u897f \u90f5\u653f\u7de8\u78bc", "CA": "\u52a0\u62ff\u5927 \u90f5\u653f\u7de8\u78bc", "DK": "\u4e39\u9ea5 \u90f5\u653f\u7de8\u78bc", "GB": "\u82f1\u570b \u90f5\u653f\u7de8\u78bc", "IT": "\u610f\u5927\u5229 \u90f5\u653f\u7de8\u78bc", "MA": "\u6469\u6d1b\u54e5 \u90f5\u653f\u7de8\u78bc", "NL": "\u8377\u862d \u90f5\u653f\u7de8\u78bc", "SE": "\u745e\u58eb \u90f5\u653f\u7de8\u78bc", "SG": "\u65b0\u52a0\u5761 \u90f5\u653f\u7de8\u78bc", "US": "\u7f8e\u570b \u90f5\u653f\u7de8\u78bc"
            }
        }
    }
    )
}

(window.jQuery);