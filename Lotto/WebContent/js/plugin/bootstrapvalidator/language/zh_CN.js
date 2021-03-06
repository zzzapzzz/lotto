/*! SmartAdmin - v1.5 - 2014-09-27 */

!function(a) {
    a.fn.bootstrapValidator.i18n=a.extend(!0, a.fn.bootstrapValidator.i18n, {
        "base64": {
            "default": "\u8bf7\u8f93\u5165\u6709\u6548\u7684Base64\u7f16\u7801"
        }
        , "between": {
            "default": "\u8bf7\u8f93\u5165\u4e0d\u5c0f\u4e8e%s \u4e14\u4e0d\u5927\u4e8e%s \u7684\u503c", "notInclusive": "\u8bf7\u8f93\u5165\u4e0d\u5c0f\u4e8e\u7b49\u4e8e%s \u4e14\u4e0d\u5927\u4e8e\u7b49\u4e8e%s \u7684\u503c"
        }
        , "callback": {
            "default": "\u8bf7\u8f93\u5165\u6709\u6548\u7684\u503c"
        }
        , "choice": {
            "default": "\u8bf7\u8f93\u5165\u6709\u6548\u7684\u503c", "less": "\u6700\u5c11\u9009\u62e9 %s \u4e2a\u9009\u9879", "more": "\u6700\u591a\u9009\u62e9 %s \u4e2a\u9009\u9879", "between": "\u8bf7\u9009\u62e9 %s \u81f3 %s \u4e2a\u9009\u9879"
        }
        , "creditCard": {
            "default": "\u8bf7\u8f93\u5165\u6709\u6548\u7684\u4fe1\u7528\u5361\u53f7\u7801"
        }
        , "cusip": {
            "default": "\u8bf7\u8f93\u5165\u6709\u6548\u7684CUSIP"
        }
        , "cvv": {
            "default": "\u8bf7\u8f93\u5165\u6709\u6548\u7684CVV"
        }
        , "date": {
            "default": "\u8bf7\u8f93\u5165\u6709\u6548\u7684\u65e5\u671f"
        }
        , "different": {
            "default": "\u8bf7\u8f93\u5165\u4e0d\u4e00\u6837\u7684\u503c"
        }
        , "digits": {
            "default": "\u53ea\u80fd\u8f93\u5165\u6570\u5b57"
        }
        , "ean": {
            "default": "\u8bf7\u8f93\u5165\u6709\u6548\u7684EAN"
        }
        , "emailAddress": {
            "default": "\u8bf7\u8f93\u5165\u6709\u6548\u7684EMAIL"
        }
        , "file": {
            "default": "\u8bf7\u9009\u62e9\u6709\u6548\u7684\u6863\u6848"
        }
        , "greaterThan": {
            "default": "\u8bf7\u8f93\u5165\u5927\u4e8e\u6216\u7b49\u4e8e %s \u7684\u503c", "notInclusive": "\u8bf7\u8f93\u5165\u5927\u4e8e %s \u7684\u503c"
        }
        , "grid": {
            "default": "\u8bf7\u8f93\u5165\u6709\u6548\u7684GRId"
        }
        , "hex": {
            "default": "\u8bf7\u8f93\u5165\u6709\u6548\u768416\u4f4d\u5143\u7801"
        }
        , "hexColor": {
            "default": "\u8bf7\u8f93\u5165\u6709\u6548\u768416\u4f4d\u8272\u7801"
        }
        , "iban": {
            "default":"\u8bf7\u8f93\u5165\u6709\u6548\u7684IBAN ", "countryNotSupported":"\u4e0d\u652f\u63f4\u8be5\u56fd\u5bb6\u4ee3\u7801%s", "country":"\u8bf7\u8f93\u5165\u6709\u6548\u7684 %s IBAN\u7f16\u53f7 ", "countries": {
                "AD": "\u5b89\u9053\u200b\u200b\u5c14", "AE": "\u963f\u8054\u914b", "AL": "\u963f\u5c14\u5df4\u5c3c\u4e9a", "AO": "\u5b89\u54e5\u62c9", "AT": "\u5965\u5730\u5229", "AZ": "\u963f\u585e\u62dc\u7586", "BA": "\u6ce2\u65af\u5c3c\u4e9a\u548c\u9ed1\u585e\u54e5\u7ef4\u90a3", "BE": "\u6bd4\u5229\u65f6", "BF": "\u5e03\u57fa\u7eb3\u6cd5\u7d22", "BG": "\u4fdd\u52a0\u5229\u4e9a", "BH": "\u5df4\u6797", "BI": "\u5e03\u9686\u8fea", "BJ": "\u8d1d\u5b81", "BR": "\u5df4\u897f", "CH": "\u745e\u58eb", "CI": "\u8c61\u7259\u6d77\u5cb8", "CM": "\u5580\u9ea6\u9686", "CR": "\u54e5\u65af\u8fbe\u9ece\u52a0", "CV": "\u4f5b\u5f97\u89d2", "CY": "\u585e\u6d66\u8def\u65af", "CZ": "\u6377\u514b\u5171\u548c\u56fd", "DE": "\u5fb7\u56fd", "DK": "\u4e39\u9ea6", "DO": "\u591a\u660e\u5c3c\u52a0\u5171\u548c\u56fd", "DZ": "\u963f\u5c14\u53ca\u5229\u4e9a", "EE": "\u7231\u6c99\u5c3c\u4e9a", "ES": "\u897f\u73ed\u7259", "FI": "\u82ac\u5170", "FO": "\u6cd5\u7f57\u7fa4\u5c9b", "FR": "\u6cd5\u56fd", "GB": "\u82f1\u56fd", "GE": "\u683c\u9c81\u5409\u4e9a", "GI": "\u76f4\u5e03\u7f57\u9640", "GL": "\u683c\u9675\u5170\u5c9b", "GR": "\u5e0c\u814a", "GT": "\u5371\u5730\u9a6c\u62c9", "HR": "\u514b\u7f57\u5730\u4e9a", "HU": "\u5308\u7259\u5229", "IE": "\u7231\u5c14\u5170", "IL": "\u4ee5\u8272\u5217", "IR": "\u4f0a\u6717", "IS": "\u51b0\u5c9b", "IT": "\u610f\u5927\u5229", "JO": "\u4e54\u4e39", "KW": "\u79d1\u5a01\u7279", "KZ": "\u54c8\u8428\u514b\u65af\u5766", "LB": "\u9ece\u5df4\u5ae9", "LI": "\u5217\u652f\u6566\u58eb\u767b", "LT": "\u7acb\u9676\u5b9b", "LU": "\u5362\u68ee\u5821", "LV": "\u62c9\u8131\u7ef4\u4e9a", "MC": "\u6469\u7eb3\u54e5", "MD": "\u6469\u5c14\u591a\u74e6", "ME": "\u9ed1\u5c71\u5171\u548c\u56fd", "MG": "\u9a6c\u8fbe\u52a0\u65af\u52a0", "MK": "\u9a6c\u5176\u987f", "ML": "\u9a6c\u91cc", "MR": "\u6bdb\u91cc\u5854\u5c3c\u4e9a", "MT": "\u9a6c\u8033\u4ed6", "MU": "\u6bdb\u91cc\u6c42\u65af", "MZ": "\u83ab\u6851\u6bd4\u514b", "NL": "\u8377\u5170", "NO": "\u632a\u5a01", "PK": "\u5df4\u57fa\u65af\u5766", "PL": "\u6ce2\u5170", "PS": "\u5df4\u52d2\u65af\u5766", "PT": "\u8461\u8404\u7259", "QA": "\u5361\u5854\u5c14", "RO": "\u7f57\u9a6c\u5c3c\u4e9a", "RS": "\u585e\u5c14\u7ef4\u4e9a", "SA": "\u6c99\u7279\u963f\u62c9\u4f2f", "SE": "\u745e\u5178", "SI": "\u65af\u6d1b\u6587\u5c3c\u4e9a", "SK": "\u65af\u6d1b\u4f10\u514b", "SM": "\u5723\u9a6c\u529b\u8bfa", "SN": "\u585e\u5185\u52a0\u5c14", "TN": "\u7a81\u5c3c\u65af", "TR": "\u571f\u8033\u5176", "VG": "\u82f1\u5c5e\u7ef4\u5c14\u4eac\u7fa4\u5c9b"
            }
        }
        , "id": {
            "default":"\u8bf7\u8f93\u5165\u6709\u6548\u7684\u8eab\u4efd\u8bc1\u5b57\u53f7\u7801", "countryNotSupported":"\u4e0d\u652f\u63f4\u8be5\u56fd\u5bb6\u4ee3\u7801%s", "country":"\u8bf7\u8f93\u5165\u6709\u6548\u7684%s \u8eab\u4efd\u8bc1\u5b57\u53f7\u7801", "countries": {
                "BA": "\u6ce2\u65af\u5c3c\u4e9a\u548c\u9ed1\u585e\u54e5\u7ef4\u90a3", "BG": "\u4fdd\u52a0\u5229\u4e9a", "BR": "\u5df4\u897f", "CH": "\u745e\u58eb", "CL": "\u667a\u5229", "CZ": "\u6377\u514b", "DK": "\u4e39\u9ea6", "EE": "\u7231\u6c99\u5c3c\u4e9a", "ES": "\u897f\u73ed\u7259\u8bed", "FI": "\u82ac\u5170", "HR": "\u514b\u7f57\u5730\u4e9a", "IE": "\u7231\u5c14\u5170", "IS": "\u51b0\u5c9b", "LT": "\u7acb\u9676\u5b9b", "LV": "\u62c9\u8131\u7ef4\u4e9a\u8bed", "ME": "\u9ed1\u5c71\u5171\u548c\u56fd", "MK": "\u9a6c\u5176\u987f", "NL": "\u8377\u5170", "RO": "\u7f57\u9a6c\u5c3c\u4e9a", "RS": "\u585e\u5c14\u7ef4\u4e9a", "SE": "\u745e\u5178", "SI": "\u65af\u6d1b\u6587\u5c3c\u4e9a", "SK": "\u65af\u6d1b\u4f10\u514b", "SM": "\u5723\u9a6c\u529b\u8bfa", "ZA": "\u5357\u975e"
            }
        }
        , "identical": {
            "default": "\u8bf7\u8f93\u5165\u4e00\u6837\u7684\u503c"
        }
        , "imei": {
            "default": "\u8bf7\u8f93\u5165\u6709\u6548\u7684IMEI"
        }
        , "imo": {
            "default": "\u8bf7\u8f93\u5165\u6709\u6548\u7684IMO"
        }
        , "integer": {
            "default": "\u8bf7\u8f93\u5165\u6709\u6548\u7684\u6574\u6570"
        }
        , "ip": {
            "default": "\u8bf7\u8f93\u5165\u6709\u6548\u7684IP\u4f4d\u5740", "ipv4": "\u8bf7\u8f93\u5165\u6709\u6548\u7684IPv4\u4f4d\u5740", "ipv6": "\u8bf7\u8f93\u5165\u6709\u6548\u7684IPv6\u4f4d\u5740"
        }
        , "isbn": {
            "default": "\u8bf7\u8f93\u5165\u6709\u6548\u7684ISBN"
        }
        , "isin": {
            "default": "\u8bf7\u8f93\u5165\u6709\u6548\u7684ISIN"
        }
        , "ismn": {
            "default": "\u8bf7\u8f93\u5165\u6709\u6548\u7684ISMN"
        }
        , "issn": {
            "default": "\u8bf7\u8f93\u5165\u6709\u6548\u7684ISSN"
        }
        , "lessThan": {
            "default": "\u8bf7\u8f93\u5165\u5c0f\u4e8e\u6216\u7b49\u4e8e %s \u7684\u503c", "notInclusive": "\u8bf7\u8f93\u5165\u5c0f\u4e8e %s \u7684\u503c"
        }
        , "mac": {
            "default": "\u8bf7\u8f93\u5165\u6709\u6548\u7684MAC\u4f4d\u5740"
        }
        , "meid": {
            "default": "\u8bf7\u8f93\u5165\u6709\u6548\u7684MEID"
        }
        , "notEmpty": {
            "default": "\u680f\u4f4d\u4e0d\u80fd\u4e3a\u7a7a"
        }
        , "numeric": {
            "default": "\u8bf7\u8f93\u5165\u6709\u6548\u7684\u6d6e\u70b9\u6570"
        }
        , "phone": {
            "default":"\u8bf7\u8f93\u5165\u6709\u6548\u7684\u7535\u8bdd\u53f7\u7801", "countryNotSupported":"\u4e0d\u652f\u63f4\u8be5\u56fd\u5bb6\u4ee3\u7801%s", "country":"\u8bf7\u8f93\u5165\u6709\u6548\u7684 %s \u7535\u8bdd\u53f7\u7801", "countries": {
                "BR": "\u5df4\u897f", "ES": "\u897f\u73ed\u7259", "FR": "\u6cd5\u56fd", "GB": "\u82f1\u56fd", "MA": "\u6469\u6d1b\u54e5", "PK": "\u5df4\u57fa\u65af\u5766", "US": "\u7f8e\u56fd"
            }
        }
        , "regexp": {
            "default": "\u8bf7\u8f93\u5165\u6709\u6548\u7684\u503c"
        }
        , "remote": {
            "default": "\u8bf7\u8f93\u5165\u6709\u6548\u7684\u503c"
        }
        , "rtn": {
            "default": "\u8bf7\u8f93\u5165\u6709\u6548\u7684RTN"
        }
        , "sedol": {
            "default": "\u8bf7\u8f93\u5165\u6709\u6548\u7684SEDOL"
        }
        , "siren": {
            "default": "\u8bf7\u8f93\u5165\u6709\u6548\u7684SIREN"
        }
        , "siret": {
            "default": "\u8bf7\u8f93\u5165\u6709\u6548\u7684SIRET"
        }
        , "step": {
            "default": "\u8bf7\u8f93\u5165 %s \u4e2a\u6709\u6548\u6b65\u9aa4"
        }
        , "stringCase": {
            "default": "\u53ea\u80fd\u8f93\u5165\u5c0f\u5199\u7684\u503c", "upper": "\u53ea\u80fd\u8f93\u5165\u5927\u5199\u7684\u503c"
        }
        , "stringLength": {
            "default": "\u8bf7\u8f93\u5165\u7b26\u5408\u957f\u5ea6\u9650\u5236\u7684\u503c", "less": "\u8bf7\u8f93\u5165\u5c0f\u4e8e %s \u4e2a\u5b57", "more": "\u8bf7\u8f93\u5165\u5927\u4e8e %s \u4e2a\u5b57", "between": "\u8bf7\u8f93\u5165\u4ecb\u4e8e %s \u81f3 %s \u4e2a\u5b57"
        }
        , "uri": {
            "default": "\u8bf7\u8f93\u5165\u4e00\u4e2a\u6709\u6548\u7684\u94fe\u63a5"
        }
        , "uuid": {
            "default": "\u8bf7\u8f93\u5165\u6709\u6548\u7684UUID", "version": "\u8bf7\u8f93\u5165\u7b26\u5408\u7248\u672c %s \u7684UUID"
        }
        , "vat": {
            "default":"\u8bf7\u8f93\u5165\u6709\u6548\u7684VAT", "countryNotSupported":"\u4e0d\u652f\u63f4\u8be5\u56fd\u5bb6\u4ee3\u7801%s", "country":"\u8bf7\u8f93\u5165\u6709\u6548\u7684 %s VAT", "countries": {
                "AT": "\u5965\u5730\u5229", "BE": "\u6bd4\u5229\u65f6", "BG": "\u4fdd\u52a0\u5229\u4e9a", "BR": "\u5df4\u897f", "CH": "\u745e\u58eb", "CY": "\u585e\u6d66\u8def\u65af", "CZ": "\u6377\u514b", "DE": "\u5fb7\u8bed", "DK": "\u4e39\u9ea6", "EE": "\u7231\u6c99\u5c3c\u4e9a", "ES": "\u897f\u73ed\u7259", "FI": "\u82ac\u5170", "FR": "\u6cd5\u8bed", "GB": "\u82f1\u56fd", "GR": "\u5e0c\u814a", "EL": "\u5e0c\u814a", "HU": "\u5308\u7259\u5229", "HR": "\u514b\u7f57\u5730\u4e9a", "IE": "\u7231\u5c14\u5170", "IS": "\u51b0\u5c9b", "IT": "\u610f\u5927\u5229", "LT": "\u7acb\u9676\u5b9b", "LU": "\u5362\u68ee\u5821", "LV": "\u62c9\u8131\u7ef4\u4e9a\u8bed", "MT": "\u9a6c\u8033\u4ed6", "NL": "\u8377\u5170", "NO": "\u632a\u5a01", "PL": "\u6ce2\u5170", "PT": "\u8461\u8404\u7259", "RO": "\u7f57\u9a6c\u5c3c\u4e9a", "RU": "\u4fc4\u7f57\u65af", "RS": "\u585e\u5c14\u7ef4\u4e9a", "SE": "\u745e\u5178", "SI": "\u65af\u6d1b\u6587\u5c3c\u4e9a", "SK": "\u65af\u6d1b\u4f10\u514b", "ZA": "\u5357\u975e"
            }
        }
        , "vin": {
            "default": "\u8bf7\u8f93\u5165\u6709\u6548\u7684VIN"
        }
        , "zipCode": {
            "default":"\u8bf7\u8f93\u5165\u6709\u6548\u7684\u90ae\u653f\u7f16\u7801", "countryNotSupported":"\u4e0d\u652f\u63f4\u8be5\u56fd\u5bb6\u4ee3\u7801%s", "country":"\u8bf7\u8f93\u5165\u6709\u6548\u7684 %s", "countries": {
                "BR": "\u5df4\u897f \u90ae\u653f\u7f16\u7801", "CA": "\u52a0\u62ff\u5927 \u90ae\u653f\u7f16\u7801", "DK": "\u4e39\u9ea6 \u90ae\u653f\u7f16\u7801", "GB": "\u82f1\u56fd \u90ae\u653f\u7f16\u7801", "IT": "\u610f\u5927\u5229 \u90ae\u653f\u7f16\u7801", "MA": "\u6469\u6d1b\u54e5 \u90ae\u653f\u7f16\u7801", "NL": "\u8377\u5170 \u90ae\u653f\u7f16\u7801", "SE": "\u745e\u58eb \u90ae\u653f\u7f16\u7801", "SG": "\u65b0\u52a0\u5761 \u90ae\u653f\u7f16\u7801", "US": "\u7f8e\u56fd \u90ae\u653f\u7f16\u7801"
            }
        }
    }
    )
}

(window.jQuery);