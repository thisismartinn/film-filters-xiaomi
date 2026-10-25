package io.github.hieu.filmfilters;

/**
 * Glyphs of the Leica label typeface (rounded monoline caps and digits), cut from a reference
 * raster: white pixels with the anti-aliasing in the alpha channel, as base64 PNG. Every glyph
 * is {@link #CAP_HEIGHT} px tall and sits on the same baseline. Only the characters the film
 * names need are included.
 */
final class LeicaFont {
    private LeicaFont() {
    }

    /** Height of every glyph bitmap, in pixels. */
    static final int CAP_HEIGHT = 65;

    /** Stroke width of the typeface at {@link #CAP_HEIGHT}, in pixels (the width of "I"). */
    static final int STROKE = 10;

    /** { character, base64 PNG }. */
    static final String[][] GLYPHS = {
            {"R",
                    "iVBORw0KGgoAAAANSUhEUgAAAC0AAABBCAYAAABFLC27AAADqUlEQVR42u2aT4gURxSHv6rumVHi34iIEaPkIEsIBIOiHoIH0Vtu"
                    + "OeQi6CkXIWCEqCB42FtuwUsI8WDAQ27iSdCLmwRExYMihMAGVBBUdNWNZGd35udhXkO77M5WzXaPvdoFxQxMT/VXr169V+/Vc5I8"
                    + "vdYEDgLHgU8AAY7htZfAHeA28BtwHZgGUqADdI0JZ9AN4DRwGFhrExh2E/A/MAVMAFeAMzaJBJgxcJCUSDqqarZxSYclpdadJJC0"
                    + "WdJde2imAqBdSR3rkvRc0g8GnUjyHtgKrLQl8rz95ozDmzqsAk4CX9vvDQ+sprptNvgWoO1tZ7oKgzvbpJ+ZdXM+cneX2ReCdqYi"
                    + "H6eRM36b0gZYD+xOI6T8zOxoWqBddrnv6wLGboZCZ4OPAads4G6BGy1TjyPAIXMkrg/0SBoAnLUWcA94XpIKXAa+AVbMcYRwuUmu"
                    + "TCOXs2ldBUnb5czamkA/MROrn10zkUVCKzeeQv5TBQ840EZ456CHaZtVhqRVMrDea/WooWvoipyZ3VKDlqULgixIOqBUirLh3k51"
                    + "CbAxMHURdfZIcgGnClSJNrAB+Cp3GHPzHI+ngQcx0G37U2KD+AKknAKbgKPArgBhTAF/xUB/CHwLfGAnvcW2BPgI2AN8GhiITABX"
                    + "0wgd3gnsKHjzNiKfHwfux0i6rPxedwFBKBco/A68SgcwTWVkk0LaGHARcOkALxm2/XbAfWAUeAokVXfj2creAv60VelUHTpb2W1m"
                    + "ZQSkfoCZDyMVNjvwHQFO0Ev4R0vaFdyJiOq/BPYBnZiNOB1gngax0z5Q2i161yuXYtJiN4ALBXrEhrnwL+ilcX0AwwiwJUbST4Cf"
                    + "zf9HBaJ93HgTWAZ8B3yfO3W6eTZkC9iTRkqmAbwqKMPUMZV7CfxoEj+wgDBawOcx+tktqSfAJHDJVtH1AW8AmwdJqqsg9dAsCzJB"
                    + "766w1efdHlhVpRgxDTwmVMojBjucOoVQQ9fQNXQNXUPX0DX0+wi9ZK/klhy0o3+dxxuTy64PVAEptwOjfO95s+hEi126AVVCwOOA"
                    + "93SAaQ/8C7yYB065WO5v4L8FAs/FqMc/lqaYa1NmQbCAPzzwEPgll4uYPZinV217jl7hlQqG7lp8OA6cZe6cdXZBdRP4FavhbEoa"
                    + "lfRIUjtX8zkl6bqk/ZKyZymhe/tcIeknSU/s3bKa00lJY5J2ZQWyifWmVfc+yEFfk7TXBmxmVbUlgy+XdEzSPeOYlHRe0vaM4zVb"
                    + "vn+iJxWR2wAAAABJRU5ErkJggg=="},
            {"P",
                    "iVBORw0KGgoAAAANSUhEUgAAAC0AAABBCAYAAABFLC27AAAC+ElEQVR42u2aPWtUQRSGn5k7m4BREEGxU1EE/QUWthbWFoKiILYW"
                    + "gqRQELEQ/4CCEFD/gII/QLBSkyIpRAQhKBFTxELNByabzX0tcjZcwibcWe7uvYE5MOzX7Oxzz33PmZmz4yR5wAEBuA7cBU4AsveH"
                    + "ZYvAZ+AT8BKYBtaBlj3m3Y7OoAPwELgBHLKOwzYBq8Aa8Ad4Czy1i3DAxha4pEzSPTXTfki6JSlIaknykkDSaUmz1qnTANBc0oY1"
                    + "SVqSNG7gmSTvgWPAqN0iT/3mjMObHPYD94Fr9tmIr0m/Za0LfsASxHGg7S0A2APgJ4ErgHxkdA+ylYG/DJwNkVqrU+cAh4FzMdDL"
                    + "lj9HBpSjy8wPWVlP53ZrvgI3t/1QFR6UjX8buGrPd7qro8CZGE8HYB5YGJAE3gGXgLEeSwhX0PXBEDlwq3AL8wpzcm5OKWOdWOjc"
                    + "miqEVmHc0jlwz1mCTtAJOkEn6ARduYU+vuMqXF+7ba1yaGfr2Sq3aK4wXqfkuIqBblshJSusgavytAeOFKoCuzo6RMhhzCpQYxWt"
                    + "8LoxtQ4cBS4Wlr2ux86mW2X6FQN9ykpnWcVxldsWroym/wEzsTuXMMCkUKbgmQNToY+BB7XbdiUu6BswHfoYfNjWBV4FngELTZ9c"
                    + "VIB+BbwGnN8DwB74ADwClgDfFE3vpvP3wDjwxTJXp8maXgTeAA8sAIPlaRcD3bGWMZj/Y2Sz7gowAzxhs4CzYsCd7m+GiOidBV4A"
                    + "+yhf6YyZGdvATwOeA36bgzID3pJnDPQy8NyCoWp9u4KnVZjIehZxYuTRsul2ozA7VR14XentWnHqpywmqiuL9ZWhQp+DVq3ptEdM"
                    + "0Ak6QSfoBJ2gE3SCTtAJeojQZRfzahJ02dKtaxL0YkRNQk2B/l4Az3eQhAMmgb+F7X6t0PPAhL3OekjCs3lIdYLNSrxq97gkJ2lU"
                    + "0mNJC5Ladt4zl7QmaUrSBUnYeU/qbthJ2czA70ias8OpuaSPks5b5xG7wNqh/wMIdSzQTYGEqAAAAABJRU5ErkJggg=="},
            {"K",
                    "iVBORw0KGgoAAAANSUhEUgAAACwAAABBCAYAAACq7kaFAAAGNUlEQVR42sWaWYgcVRiFv66unpkYFZcXRXHBB1ERjQqCMdEHIS4g"
                    + "giAaRciD4kvc4jYuibsRjTEGcUHwIUbwwQdBoyJijIIgCRECBkGIIiQqLtFo4kxP1/FhztXr0NN1u6e6LCi6uruWU/89//mXe5E0"
                    + "IimXdJWkrzS9/SHpQ0mXS8okjUpqSKLCvRHdM5fUlHSQpLWSfpLUljQh6W1JZ/m8MSS1JN0vabekSQMuDPpbSbf5hq2KQQfAmfeD"
                    + "Ja2TtNc4Cu/7JX0i6VxJIOl2Sb9FQGdueyUtsQXyigFnvu+YpGfUe/tI0nFI+kVSpwvYjqQpH78l6TADrsrKmYE2JC2fYdWZODqS"
                    + "fpe0MgMOBzKgwX+3+LfTgUMB+fcqtiYwASwG7gZa/r0bjoBlcWYQKTevCihADkwBJwCPAscYR6PkOuUJJ1W9hRcfA8aB80vAKvrc"
                    + "mv0PYEd8PA4sSxjh8DI7gA2pgKsYhYap9RdwMXCbqUGJdTNf8ySwM6/Ruk0DOAd4GDg4gQoy19cAm4CxrEawAAcBDwALgCJh5DLg"
                    + "DeBxg5/IauJtkKW7gMsSwAbLfwY8YUo0gGLYgBvmaRu4Elhhazd6AA4v8zNwP/Clr5miYm2dTRE6wCLgQUtZGW8bBvc0sBkYDWDr"
                    + "ADwFHGknO7mECopG5QVgrY/bsfRlQwQbnO0O4MJEJ2sAW4DVwGTgbbcbV83blh90E3BLNNRlVNhlFdkThW+GDTh3UrMIuCeKbGVg"
                    + "21aELbOBHQbg3E52qrXz6ATehn0dsCG6h4YNOESyMVv2vD6Cw/vAY5FVizLnqMLJQuY3DlwdgS2jwhfW29/9vZPizYM6VyNKaiaA"
                    + "S5zUtBLB7rc+b4uCg4YFOOZtAZxtvZ2fmNQUwLPAu3bMIqWYmAtg2SoFcIjBnpmYJ2TAm1aFjq/ppFqnXxqEz/Cyo8B9pkMZ2CJK"
                    + "alaZEtlsEjZXC2cR0KZ5OuWk5uYZnO5l2X3ASmCn79Pp17tTto5DZRYlNW3XY+P+nlKXTVlvN1v+OolFcDIlAoAR4HoPf+DuXuAK"
                    + "4JTEircBvGre4hcu+pYmSUq0cDsKDiEpGU0AG/7/1C/9TRR6NSzAZWB6OVnmZGapqdAaFGy/KqE+q2lFCrAa+NgjMjko2H4BD1Lq"
                    + "TwLrgZcNvj0XsFVQoowqe4CFznNbBlxJZTCMJF4uj26KcuKsTsDqspdtI06GbjCX59zLG7ZKxNRYasfL50KNQXU4rt1SQW8BrjH4"
                    + "5iBRLgVweNhu4PkZgWLSCc/CPvT4FWC56VEMEukomVfo+HOXpGM9mzTPsz25J0q+7jE/oui/sN8TzWtk/U41pDpdM0qyp1xd5MDn"
                    + "wCPAgUgZeqlGA7gTWOJ+WbMulSjM6Rx4HXguqhjU41kCjnCyf5pfvlWXrMWVwpPAOxGoXvocyqlVUY+4OQzAs6lHBvwGPARsTwQt"
                    + "J/738u+ET1YH4AA6d+X7oMGn8DlzG+viSDIbdQCWH9hyBfxUVPaU8XmeqXG2+dysAzARp+U26cYSK8d8XuCROdzfm3UlPyEIHLAK"
                    + "bOvDCS91e6uUz1Vna4X5vMtD/UO3Hm+X7lHmyvvasiRpGOllcMJN7mBOJDrhqHts5zjsN+uycBE50EvAi5G1ykCfFLVpO90qomEl"
                    + "8EVUIq0GPkhsAwi4yPo82g3jMCdlgpW/dytrVwmfY8W50XvHcpnVAZjogVudJE2UKEfcuBmPgkpo3w4dsCIuvpaYJAVqHGV5PDGK"
                    + "hNQxdVtESdJqq0fKwpLCirGS6TlqAVldk+MhSdrrqLY9gRrB0tcBt/r8vM4FHh0P6yBJ0l2OhpN1AlaUsIckqUhIkkKHfwVwfNbH"
                    + "w6porhAl/muZnpdrJDrhGcCy1FVVGdUtZgrDHJKkrSXOH89ULU4Fsp8+5iESg0qL6V7xSgeX2fgcr6r6px3abUjiEz8Dfk2MVKk1"
                    + "YShi32O6K9+eEdbj64J2f4LXPP7Zo6fwi6QLvOQwr3iFa+ZFqGOS1pT0SDZLOh4vx31A0o+SDkQnHJD0naQV0ULRqpfk4ntnkuZL"
                    + "Wu+luBNRI2efpE/j1a253/BOSTt8ciFpm6Qrh7QUN17hGoOeZwN9Z8D7JG2UtMDnjfwNvqM9RZaame8AAAAASUVORK5CYII="},
            {"C",
                    "iVBORw0KGgoAAAANSUhEUgAAAC0AAABBCAYAAABFLC27AAAECUlEQVR42u3aP4hcdRDA8c/bfXe5M4mYECEgSqIpYlQiGFFTiBZW"
                    + "ioWoIIgg2NiJoJ2SQisLxcJGsElEtLARC1EiQghRr1D8g4RIxD8gSSFqNDG7t2Ox83LP5XK7F3ZzL7IDj32399v9fX/z5jcz+5sp"
                    + "IsKAzGAx76/D/bgXu7AFbZORU/gSH+FtHMu5ejhbH1jUoIsctIir8TgexpW4DHMTBIbAmYQ/hQN4Fb8l22KOISJERBERs3l/R0Qc"
                    + "jmbIJxGxN7lmk1MFXOY/bo+Iz/MDvYhYzNfeRQStzxsRsZCKVHHWV3B9TcOLFxl0JfiIiEMRsb0CFxHtiNgYEQdqwE2ROvj+iNgQ"
                    + "ETOtNO4H8UDeF5ojRV6Bh3A3Oi2sxyOYHwE6JnitBA6zeAwbSmzFNatY+VpIoIXd2FziJlw+ItTvOJ1fMC7ppRY3rTB/9f48dpTY"
                    + "iXLIKouMVs9lpJrLycZhs2czmL2M64eY6AyuLVPL7RFM4jN8gM4QG7wQ8O+xkNDDpF3W8oxRZL72SMcB3krouQzhI3mvchWbKxJ2"
                    + "cYzQ1QaLEVl66LY0Q7qZGHWHLBB+WGvoStOn8WGaiFpGF7UnXOAnfNNa5YYZt7+O1G4LH+O1BGzXomGVMv+Kl3CyHNE2Jxneewnd"
                    + "wfOp5SewId9fxC94Ee9W3qMJ0qsB7ku4O7ERP+IIjuaYblOgDQSrBXyRHuVMLbB0EU2Crvv/iqtTu+9Wptw06PrmLJZxd5oKvSzo"
                    + "YBi95GQKPYWeQk+hp9BT6Cn0FHoKPYWeQv//oIuVjiwuSU039TdiXGrQxbBFNBW6Ov4tatDnjpdbDXQMbUsHM+ssnYlXh5KN0nTd"
                    + "Y9ygf5a3Hcf1OxOO5aJ6ZYM0HKnNZ/GkfrWrap04hhfwDlqrKV9MUsNVoWofnlnGAm7EK/qlu7daDQHu4NbUcFW8qlcCuvpF2qex"
                    + "pWwANP3q1n36zTBVSaNYxny2Ys+Fli/GHUjW4ap8/MUKm7TAttVoujqtH/Sh48h9qsptDMk7CnRKw+t31RfN45+aDx2HVIXXvyxV"
                    + "sFZSSFTe40+jVW1vwV36ZbGZMYG3U8PbcNuIn+mW+NZAC9l5bHkn3syx4+5CmMMVI+ydDo6X+Dr/MOTRFNi8hllfZfdHW/hZv5I0"
                    + "iodYi86aiquHgzjRwt94PV+H5bLFBK9hufUf2I8zrfQeh3IVhfH2cozTNN7HV2iLiJlsc9sTEUca2pd3OCJ21fvyDHRALpynE/Fi"
                    + "gQ7O+2mtdbNddUAO9pruzR7PJsjBiNidXDNVr+lgV28VojfhKTyq37e3PiPiJNPYbkbc0ziRufMbGczqmd9/oCuZtdQRsEO/Hfke"
                    + "3JzwkwI+ie9yw72Xib9a6npO/gVIW75LkGsNpAAAAABJRU5ErkJggg=="},
            {"F",
                    "iVBORw0KGgoAAAANSUhEUgAAAC0AAABBCAYAAABFLC27AAACrUlEQVR42u2au4sUQRCHv+lpV7kVBE0EuUhDowsVYxNTAyMDQ00F"
                    + "M0NBMBFE8B+QQ1APQcVAFBFBOQ0NFcEXouL5OPcx8zO4GhyXPW57zpmdhS4oZodhma9rqqu7qgtJXhKS9ki6IumjpIHaIbmkN5JO"
                    + "S5qT5CSlGPA+SbckrUjqq13Sk/RZ0iVJXUkJknZIuqbZkPOSvAMOAoeA3LSNkgECjgH7HbAAONOkpdAFWwosOGC73cyCJEDuzfRt"
                    + "F9n1N7DsA1xCUwROTO8CL30ATDJFlxBwGzgHZD5gtF+AHuAbdolvwCJwGXgPpD5gtEvABWBrg6ExA1aAD0DfDDYMsVoOvAJWp+Ae"
                    + "ziJcRuCnToCO/TFveGL+8z4f6GN5aXWa2urpmEGJ0BE6QkfoCB2h/7uE7j3GXevemm4KOi0lwHlNXykZt0HaDPQvYFiCVw3AGTCw"
                    + "397eVxk6B+aBU0C3pmTYAT+BF8BTy1jcuN1kSOZy2LROX84sybgOnAXejgMPge40EBi2ANuAE5bWnQS+j4K7CjO6bi3gjgJHSulW"
                    + "5TidNKCFVTvAcWCnDcbNwuKSALvNZWZiRSwm+zurtVSO0zSUgRdlsAFww+oerhyzfUULNGHlReCqwboqJQTZyOsGz80dloAzFu7S"
                    + "0cUsBPoe8BiYo77ycAI8Ax4CP8zCWVWfdsBr4GLJInX5c1HkTNczTohPdy12rlJvWcyzQQUrtADZRFlMG82b0FreqDaeAMQcMUJH"
                    + "6AgdoSN0hI7QETpCR+jmoNUm6ElhfJug0wnyvQHwlZZ0ljmrMwzXybCLZHYAPGLtCKPOEsLE0HeAB/w9BBp97oDnwBPqOWupkPau"
                    + "dfXulXTTunpza0bt2/19SQckJaW25akqklK72WXtvj1JmaRP1k89b89dG4Al8Qf0wcuIxVGcVAAAAABJRU5ErkJggg=="},
            {"0",
                    "iVBORw0KGgoAAAANSUhEUgAAACsAAABBCAYAAABIMl38AAAC+ElEQVR42u2aPWgUQRiGn9lMkguoiRgFE7Twp7GIIvaChYikEIK1"
                    + "WBmEgJVYWCtYi2BtF9FGtFDQiNgEDYiFWIiNIEQl/iQxP3f7Wuw3xxJylw3ZsAHnheHYvZ3d5/vmm5liXifJkUmAAzyQAMvAQeAc"
                    + "cBY4AuwCOihXs8B7YAoYB94C80CXMaThQZeDxUDqwA7gIjAK9AM9QG0TQEOSFqz9Bl4Dd4BJ+y9tAktKJDlJXhKSDksal1RXdZqW"
                    + "dF1STVKHMTZhvd0ckvQi16khKbW2mUpzrWH3liXdkNRpfAkG6ST1SXqyArIqBeAlSWPGWEuAbqATuAqcsfpw1qpSYhydwDXgKLCU"
                    + "AIvACeByDrBK0JXAA8bmvc3w88B2m33JGjO3bBVJzCngkAf6gJO5SNwGX7wZgWwDhj1wwNbSIvplC3aZ6+1Oq812qgHHPbA7N/Su"
                    + "xdA74BswBryzTSItaejHgAvG4Fo854EBb1vaWjuMA74Cz4CZkmv3ATAC9Oa+tVIeGPTrqENZRv/YdbpByJCoRoHgHdDpW2SxVYew"
                    + "T6sE2BBwUjBRjYTqVbSk3FaAXdcuEWEjbISNsBE2wkbYCBthI2yEjbARNsJG2AgbYSNshI2wETbCRtj/Frbw0ZZfR0dHdgzaQfsj"
                    + "qKIK70kLBuT9OrK7BMzZbxlq5EbXFXh2xpP5DVRgmPqB08BHNn4c6nKjM0J26t1utNIAO537+GodwvV+4K4FV4bCN9vZrwJPCswF"
                    + "2O/AvgLZ6K1oEs4DzxPgBzDB2saIEGnZrcj3ZoHHwb1xH/iZq4922S27FanvCeBTcB9NAfdynUX1Cifmn4HbQD34upC0V9LTLejr"
                    + "umRGuRoGGoCHJL2qwDGnVRxzC5JuSuoOFr8A68xGh6Rjkh5JWqwws18kXTHQJHgRnaT8WppYvfaSeb1GgUEyu0kPmVVkM7QI/LUd"
                    + "chK4BbzJ1W4KNGHz6rLfOrCHzD87TGaxG6R8b9cS8IHMN/sQeGnrarcF0Vyd/gHg4nlWZGghAgAAAABJRU5ErkJggg=="},
            {"1",
                    "iVBORw0KGgoAAAANSUhEUgAAABYAAABBCAYAAADVP1R0AAABkElEQVR42u2Yv0odQRSHv527IWoa0TK+QggIgZAXSOszpLew8iVE"
                    + "sFSfIdgFxFIkRYS8Q/40FlE0+Pe6n4VnYV313r167eYHwyzD2e/MnDnT/FAn1aQuqPtq31tVDlcdc6Fuqm9V1JKALqq/1TOfpko9"
                    + "UbfV92qB+kn95fi0pb4pgWVgGpBbFTxNVcwfgc8lMA9MPANIY0MF8Ar4kIDeGKDtBFUC/gBXjVKMRQlYAQ4j03UkGDQ6g78Ca8Bx"
                    + "oyyDRqdylBG8CvwFFoG5uABboD7wGpjpkqBQUyNwNlpvKtqnaJzsElgCvjwCrjfyD1gvG3XrxeLBI5uYbCUbqLJ1MUWs0XowKcrQ"
                    + "uS3L1lFsvKDmBRsdM1K7vYgyOIMzOIMzOIMzOIMzOIMzOIMzOIMz+I4jMDRuFHDRcgsGqTcKuA8cxTws7n8XcO2qnAI7MVfc9zVq"
                    + "ryMBP7vuuP5hD9iN7/TAfSXgB/CdsGW7jJ5ahNv6LdzX2ua9VI/VHfWdyijg2hImfOIN9TzgB+q6Olf7xzfsYsrkCMLYmQAAAABJ"
                    + "RU5ErkJggg=="},
            {"4",
                    "iVBORw0KGgoAAAANSUhEUgAAACsAAABBCAYAAABIMl38AAADu0lEQVR42u2aT2gVVxTGf/fOpEm0Fi2oCK1IVQpu3NgSqKJuxCrY"
                    + "VbdCN6WbQkEXgluhunAtuHAnLkp1W+hWUENAQVwY0EK0m2xCahuTmJmvi5wrl3Hy3kzen0lgDlzee3PPu/ebM+ee+50zF0njkpB0"
                    + "XNJvkua1Kq8kXZA0Zv1ekrPvjbTw5aSkKUn/SsoM7DtJs5KuStoaAW4U7NeSJtVZLklKJY00aV0P/AAcAnJrsWT2+SNwAFgBEsDR"
                    + "gHjgsE3urRX7AT4FPgdEg+KBnUDaRS+PdNQUaA+MlVi0KK5pqwaw2UYAUhWsY5NIAOs2C1g2k2VbsC3YFmwLtgXbgm3BDlTSIRuk"
                    + "J+KeDhikj0CGa7LMQxsFbKCeK8B2YNzA/QMs2LxZ4UYaAeuisb8FzgNfAsvAQ+Am8DQCXD1lkjRjBY0yye1zTtJpy9+TDrl9qNqM"
                    + "W3FkrmTMB5K+Mf20Th3C99mioabwPfCLuUAeNQETwK/AXnMT30To8jb5ZwZ01AD6ksV2zNzjozpple+jVYPvfQHsL/hvWUp/Fthh"
                    + "v/2wLRtAjEcrvZNsNbcZ+g6mQvWmymNd4cPa2tC3W1czFrtBxtm1JnA9jFM2pnoBGyzh1wjicX+dMZMSzuCia3ldsLJt8oMBSiZY"
                    + "rDhmBrw13yUa00ULNNxMDiit8ciOAvssNmZr6LwDdlVcOGPAGYseeWSIBPgPeA68BObtWuYkzQB7ulg5q2Gx3AB0M8SyjZlGLiC7"
                    + "6dwATwHXgPvAaFWwTcoU8BPw2NegaKrR6NOYGXAEuAxsS2tQtEGURV2FfgFfAfu8LYqNWvl2kQ/v9sArc/aNKMEdRoAlD9wBljrE"
                    + "z6aBOjPotAduA3+uY/cZhgskwBxwHZgNKcNBSfckvZG0ot4lr6CTddFblPSXpJ8tlRpxksKK2wGcAk7YLpXXiACKMoVtRqw/iR5j"
                    + "UdcBL4A/jNfGr7dyYAvwDLhru5iA3Eki3n+BjzuQlU6S2I40AfzO6ivUMrAh1ZkCzhU4R0xwFixShSz4PTfIItLwdp0+llhU2VLR"
                    + "90P+tVAgMkWffW/1tHDHeSGfqrMY4n297opnDX6gbnx2vfWoXv+XdyLebcmzBduCbcG2YDvHyB70BlqYSyoC8U2CDYTlTUX9xbrW"
                    + "HYRlXxthLiMnMbhJVl+IVOYh/QabAn8DN6LqStH6HpgGbhnDq84p+ny4MbEXGqOSrtgp0eWI/S9JeiLpO9NL6xx1HcRpTG+gRyVd"
                    + "tHO4ubVHdk4X63emX+mo6//SwMHQC+jumQAAAABJRU5ErkJggg=="},
            {"5",
                    "iVBORw0KGgoAAAANSUhEUgAAACsAAABBCAYAAABIMl38AAADjklEQVR42u2aPWsUURSGn7kzm0SNKEQRNYUIioVN8B9YBPwBgo2N"
                    + "GNEgWIi2fjUWKoifhfYKWqi1jQgWImglCiKIoPhJjBiT7M5rsefqddQ1s8nOTDEHLruT7O4898w55773zCApluQk9Usak/RCbUtV"
                    + "nKWSXkk6JGmRMcWSCAcGOiDppKS3kmZUjk1L+iDpnKQlBuiysLGkcVXLTpsDG5IiD+uAIWAXbWtRrqWAgJ3AOqAJOP9PB6wHVgXH"
                    + "ZVpkowFsNnD/Nxyw0h9UyGJgcQgKkACzFYKUvU4BT+zYD5IcXlUBoN6Tt4BnQJ/F7U/P5omnXsergDvACeC78akb2K/AhM1WPbj0"
                    + "E8A14BLw1uK2GX4wmWM5ccBzYCw4wUICzwKTBjljoH+U0TyeTYDXwLsehYEzyPhf9T7J+YMNG97jCxWr/mq1Ol2xvLBpsMqkFGxl"
                    + "r1g1bA1bw1bF8pQu2eRcD7VC2kk05a2zsz2SlEnwGtbyrmFbwFpgzV9mr3mE4ZSJpCngc8DVzP5ukiOuNwK3TXUtpNZNTXE9BW4C"
                    + "d01/eG2bduPZQRu9sNXAJmDU1N1Z4HqgwFJALmeC9Wr4GF0CjAAXgeOBaHJklXiJO4Uo45DFwEHLk2OW1C4J6KtgYZxGwGF7PQqk"
                    + "DngfXAZVBNoFdf0AsBVoOuClZWNUIVgPnAIDwD5g0AEfgSsWH35LkRY4NIdYHgG2JAZ32Qr+uGVk1WwA2BZJ8vHRD+wA9hh4Uf2C"
                    + "lQbTqfHxDXgUScqqrxVWOorIfAH7LYmS/2iS1x7WfzkuMMliW6FGbbVaHngya03gjcu4vFVwRfAO6krPFlVroy7OF5W1U+jGIar3"
                    + "YDVsDVvD1rA1bA07X51Qe7aGzatno8zoVsrl2Skor/iOAi872y0U8USHP8cn5nZPzSXB7LwXh4Gl/N4HW6gdru+yREHPYLudTx3O"
                    + "0wI+JwFon3U+xu3LXZWXHOXK97OGOoSCn0ATeOVjth84Rfuud6OCuTUDPPewe82jBDOuwqLgPTsB3Epod513B7ERV8yrAu4DDxyw"
                    + "AVhWwbrrH8r4RrtxmDpgmhJux88RVMB54AHQSIAXwJfgQ3FJlzpb3prAGeCIvW/6/uzVYFUpS3n54ZmOG6hfPFLfn71Au/U4ZvFb"
                    + "dPmapv1o1FfgsXn0Hr86jSmA789GtmL59uNwgeVrhva9r4fADcv8SVukZsN8+gGpXPXx1BjIPQAAAABJRU5ErkJggg=="},
            {"6",
                    "iVBORw0KGgoAAAANSUhEUgAAACsAAABBCAYAAABIMl38AAAE5UlEQVR42u2ay2sdVRzHP/PIzUNvTaxIFyouTEHRBCroRiNYUJCi"
                    + "+EBculJ8lrbifyCiiBXFJ4Ibl9XiCwpdVCui4ibWjaKixGJBMWqNNknvzNfF/E5zCMmdM3fmJi3cHxxy5mZmzvf8fr/zew6SqDEi"
                    + "ScM2n5H0haRMUi7pF0l77P+ppKTmWtQFmtp8p6QvJS0ZUElalvSbpKcktez+eDPARpKG7O/Vkma1PuWS9tn9Qwa4J9AxvVEE5MAY"
                    + "8AQwbdey/8tGZvc+AlwKdKDnNXt+MDZwNwC3G7DIBt7cXY8Dl3ibyDcKbGKLXQQ8aUDwgK2WgOP08KqN9B1sZM8I2AXMlCzs1GIJ"
                    + "ON4ryF7BxsBpYBLYDaSeCqwFNDI9fRX4ERjqVQWwxaoATW3BvXaoVMKtCJgFXrdNxnXAxhXEnwDLwDXA3avEvB5XTwEvA/O20byO"
                    + "GqQVNtUBzjczNGGAyjb7KfC+Z8LUb511hyoB7gLusTklXJ0HngP+boKroWBj48xW4FHgvABdBXgPOOo9T7/B+qbqDmCqRJRuE98D"
                    + "L9qhUl3xh4JNTFengX1Aq4sDcECXgeeBY574+w42tsWHgceB7YGLfgMctGezJnQ1BKxzqzOmAgQ4gH+Bl4A/6trUKmCdqbrAxH9h"
                    + "gKnKgAPAuzbPmhJ/N7DOASQWUd1Y8g7H1T/NASx4UqHfYJ2pmQAeBkYDTdU7pq9pU6ZqPQ/mh23O4O8BdgSaqmPAC2aqkhKnwRqu"
                    + "WmucibzM3cZmeq4DHrJFyw6VgP3At2baqnLVD9TVzS6nqxbO7FA9BrQDxJ+Zp/ocuLxH/98B/gP+sfm6YWQkye0utZt3AW8DWwLA"
                    + "doA5T/R5YIAtT5ILwA/Ax8BHFvfKe7/3VJE5uoxzq6TDlpHm2lhalPSTpL2SJrxUP/JTcf/H+yWd9lLoEMqtsFFn+GstSzokacpA"
                    + "pj7YIbuYlPR1RaBNktu0o6OSpo2JiasbJMCIpSpTgTa1H+RHeJk5o2fNe2ZA4kzVNHAvZwdFXlxxiyWmLaDlOPuAl6pEZwlgRw8C"
                    + "VwFLsXF1Z6CNVB9Gt1BAVky5z6nBrZaqEMDVqA+jjDkxcDOwJQWutwMW4mnmGxKzU7cRy5jLMpZxoJ0CVwSkKx3gDeDNknpBVb28"
                    + "0lKgbeucF3c9ClyWmgrEJRxYsBBwtuGDNAf83gWsD7qdBmafke0ubihejT2OhUopS73EsIxyb2QNqUEVdYpjziEagB2ArVhMbpLU"
                    + "g73WQA0GYAdgB2AHYAdgz113G9r6cfm8S9+bYFIVZikNBCqKPmzOSm+rDmUGdLFCnSIuS1FcNN+2is08NT5uWMXZU1az2BZQBhDQ"
                    + "iSQdoah0j5UkbRlFy6ipqk1myWq7y/ty29gccFsKfGAp8VjJyxPg4k06W8eBEzFwyHQn5KBtZPnIqUYGfAKcjIGfga8CRbuR5aPc"
                    + "q1l8COQxxcc2+4ET9KGFWTOTAHjFmNlC0oj1E3Z7JfpMm0d+BfygpLbrebjmR2Kgn/FuzDawEZKvUaY/bK2D2PUVXLcmsR9GJD0t"
                    + "6a9N5GxH0gFJ2w2b63mc6YPh1bEAbqLoMO6gaOKNsvLFW9PkmnaLFM3q14C3gJNmLs90132wDvCwPTgOXAvcSfHNwWQfAAv4FfiM"
                    + "oml3BPiOogSbs9K0E8D/oAmdrIZ1J30AAAAASUVORK5CYII="},
    };
}
