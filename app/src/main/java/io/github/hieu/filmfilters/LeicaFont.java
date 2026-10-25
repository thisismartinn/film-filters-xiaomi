package io.github.hieu.filmfilters;

/**
 * Glyphs of the Leica label typeface (rounded monoline caps and digits), cut from a reference
 * raster and condensed to the proportions of the camera's own labels: white pixels with the
 * anti-aliasing in the alpha channel, as base64 PNG. Every glyph is {@link #CAP_HEIGHT} px tall
 * and sits on the same baseline. Only the characters the film labels need are included.
 */
final class LeicaFont {
    private LeicaFont() {
    }

    /** Height of every glyph bitmap, in pixels. */
    static final int CAP_HEIGHT = 66;

    /** Stroke width of the typeface at {@link #CAP_HEIGHT}, in pixels (the width of "I"). */
    static final int STROKE = 10;

    /** { character, base64 PNG }. */
    static final String[][] GLYPHS = {
            {"R",
                    "iVBORw0KGgoAAAANSUhEUgAAACgAAABCCAYAAAAlkZRRAAADYklEQVR42u2aOYsUQRTHf9XTe7iuLngHiyIemKy4giIIBmsoKGKi"
                    + "fgxBM9FPIKh4hIqJIqgYGZgYiXijCIqy4oEGiigeuzM7/Td5jaXodHXPONNBP3jQPVRX/eu9V++aQlKfJCQtkXROUkPdoSlJDyWd"
                    + "krReUixplqR+Sc4w4SQ5YBFwDNgGDAGO7tAU8AP4AJwFTgOfgBhoAELSoKRjKgddkLTUpNcnCSSNSZqUlEhq9gBUum5i71ckjZqa"
                    + "+yNgJTBoau2Wan1yQGTPAnYA+4HZgCJgxBvQS3IeyL3ABqARAU3KQynIBcAeYMgXbVkoxTIBrI5zfJj8B2m5FlIcATblAdhtO+0D"
                    + "xuMAcTvgNXDD+01tSC2xYLAVmOet8acE+4FVoRL8BhwF7gM1YKYNd5KY+k4AuzM0NicUYA0YyLCdPKaS2KaztJfEOU5W01NvJw5M"
                    + "LWTduIALaMcGldeOyxBBKoBFVZ1UEqwAVgDzZzKlOsVBjjrugeQii+uDnQTobGzKRUNdP1AHlgEbA6Q8lSdZaFjC4ArWMZHNMWxF"
                    + "0aq/5IJ+DtoAJvOoeBxYUzBZ8IuhCUtWs+aYBm7FgROPAofbUK+z74aMQ7L4r8DNUAmGTJyn+ApxMy+Bt3FO1/C/fa+8jPsy8Cku"
                    + "YEfdoPvANSApU6hLbe8bcByYBAbKGItnzFeWLllIT/oIsA9YDkxHOVXQCQ71uRN5Jeg6xEkLoKkUa8BOYGHoKf4BfOmAo54NzAr8"
                    + "ZgxYEdqbeQMcMaBFQ13arN9soW74H7HYbx6ty5Ms3APu8KsDX0SKfcBJ4ABwMMO3DgDjUc7Ja21wZFwHLgKPMiJUDIzm7c3MeL6q"
                    + "qB064D3wEFjbImJFwPxuZtS+q6kHbNKVvWgS9jdEL2NvVRdXACuAFcAKYAWwAlgBbJl6lRZgrh616yHIIIBRJycMpCSw8HIR8DnD"
                    + "HpRRbBex+++2bpad1iPgeYt6N202vrNCx7UJNDGATeCpt3H95QA1gdsR8Ay4yu9XlPBaEA3gPPDK3tu9CNS0da4Dt/5hYg54AZxB"
                    + "UiRpsaTzkr57l7wk6aOkQ5KGJdWM6QCn82yR9EBS3VuzIemJpO2SHHahELtxdknSjA2clnRc0lzbRKfAYTfbYnveLOmuB/CxpF3p"
                    + "7befjFaRmVs1UhoAAAAASUVORK5CYII="},
            {"P",
                    "iVBORw0KGgoAAAANSUhEUgAAACgAAABCCAYAAAAlkZRRAAAC20lEQVR42u2aO2sUYRSGn7nsJhGDERS1sDCIjUVsLATLdKJgYeUv"
                    + "8B8oFrbaCUIUtBFTWQhiY6OSH6CgqUTFImAUjbcixp3deS1yBj6GLDu3bAb5DkyzzJ7v2fe7nXP2IKkjCUkHJS1KSrRpqbbXNiS9"
                    + "lrQgaU5SLGnS4UESgaQA2A/cAs4Au4CA8dgG8Af4CtwH7gJrQAT0ASFpStJttcMeS5o19bqSiIE54Bwge0LGa9m4gXEEwCVgFZgI"
                    + "gVmTNBjj1LoWOKKkwFngKjANKDS4NlgmkICLwEmgF9Iuy2ZwGrgATLUNEGc9zgMn4hJfTLdpSoepOAOcKgM4brUj4GhcUO6fwFMg"
                    + "cT6vqlpql8E8sNcZI69gBzgWl5jee8Bz+2K/ImBovvYBd4DzIxSciUs4nnDOrKDm1PWA70U2TFxiZw2cUz+toaC2mNahY8YVjgDV"
                    + "WIPK+WndzqwkuQf0gB7QA3rAnQvvR1pcwmFk78c172L3Tm4MUBYLDizUGtS8i1OgWyTbKwrYB44Dh5ygs2qolQBHgNNNKJitkz3A"
                    + "ZXtfNddealnbZG6MfBSfACtFFewCB7YpextmPWA5rrB+ms6Bh1kCvIwbdNi0sh+AV207qDO4PvAA+NRWwBfAEyBoE+DADvAV4Dqb"
                    + "5bfOTm6SvGoR8BG4AiwZbL8NmyQ7G5eAG1bB6GRpblHAHvCjgYPahRKwDnw2qAXgi525SdGbJJP/t/26tZpXXR5wFXhjfrN6TOKK"
                    + "UKb0sQw8c5w0NbWRPYOt/JYJt7pOyJU2BBiZr17dcAsn1AqpXt3yIb8H9IAe0AN6QA/oAT2gB/w/AYM2A0YFUsmd6qkhBH6NABTw"
                    + "d5x5SB7wPZtNXlv9h5vlxW+tLBHRfPfHSMB3WCUpt2myPq4EWDTAkOoF9GomKbDewYeS1iUNnG60b5KuSZqWFEkK3d6+cTw4DYWH"
                    + "JT1yGhw3JN2UtNvg4nHDSeIf4wLBkCksvXAAAAAASUVORK5CYII="},
            {"K",
                    "iVBORw0KGgoAAAANSUhEUgAAACgAAABCCAYAAAAlkZRRAAAGMklEQVR42r2aaahVVRiGn73PcPOKouWEQRTesKIiiMwgafqTFWYK"
                    + "JSYFEYYVRAYa/TAoGy0bLG2ACiqsP01kYWZcSsFMGwwaSaFI9Dpr6j333HPefpxv5eJw797r3LN3CzZnXGu/6xveb9gLSbGkDkkP"
                    + "SDqsxjgqaY2kCyRhv9PmFdmFpIKkkr2fL2mvpD5J/ZJ+lTTbfisjaYSkpZL2SaoZwLqB3Cppmv25mBHA2AM3R9Kfdl93VSX9KGm6"
                    + "pAhJSwyMA9Y81kmaaLsuZACwbJ+vlrR7gPu691sknYekA/ZlMzi3o39MDVmoumyb7JK00e7T33Tfut33oKRlRWAUA48YqAPDgbNo"
                    + "fxRszQJwJzDV1i80/S+yqwhMiQElLOp+K7cJLgJKQAWYAcw1sFHCnBjoSPtTM9ChjjLQB5wB3A2MN+lFCfeqAXti8h8lex0JLDTV"
                    + "yiSUJJDjwPq8ATqbqwAzgVvMtkJMoht4J27BhoZidzFQBS4EFgAjTDpRguQiYB/wPLArzll6MgneBUxJsDvf9o4DDwObnJPkMSID"
                    + "VgNuBm5IkZw/by3wtm1GeQEsmteeD9xvXBui2p+BJ4EDtsFqnKPXjgUWA2emeK0DVwHeALYaLeUiQafaKnArMLuFue8Dr3scWCOF"
                    + "i4YqvV7z2tuBjhTVOqf5BXgU2Guf+3xPy2q4cDgRWAJMNgBpqj0KrAB+8iIOWQMs2CXgNuDqFrz2PfPaGOhvDqtxhnbXB1xptldO"
                    + "IXin2t+Bp4BDnu2RNcCi7XwMcB/QFUDIsVHJYmCb2W51MLZvV7XudQEwLVC1NeBd4BObW08KR0NRadREKVdZODspkJA3AM+Y5KOs"
                    + "AfrSqwGTTLVjAgn5MPCq2V/R1lCWAOVJUCa5KwITgX7gFeAj48haWjIct+G1VeA6YJ59DqGUzcBK4z4ZYLIAGHtc12HS6rK0aGyA"
                    + "9CLgL+Axey02E/JQAUaeamre+2HAPcA5Aem7G2uA9caRqar1OSxkjAFutLTpmIWzeTZfAfbaDSy3zcUDEfKgEpIUspODZnOOs8pW"
                    + "BIU4U4/VImttXrWVKjFUgqMSACR5bQV4GfjCwPW3WsIWW6CWVgopZ5cbgBdNpYmE3K4XRwNcaeB2AU8Auz1SJy+ArZL4QWCpqbYU"
                    + "wnf/F8BmsPV2Fwn14qFI8W9rEn3ppWS5SVADXEn2WgdOtdR/wiBtttydJA2kgEuBOzzHifNS8QFTkfPG4UBnyhxXMO207kL3UIg6"
                    + "DaC7yWrgQ4sex4DTgXuBUwLKyhjYaCB32udKuEUnD9f1X2E95k7rM3dKWuX1sQcbda/n/KzNG9bKE4M4wDn8shIvF1wOfM+JXnZS"
                    + "NhQDNwHTbW4xtKUXarR1rx3R65WMDwF7vFZbksNMsA7raZYLlvMianlSWAO8FRjGBFxk1d/w0FwgboOM3esLlohGKVJ0vZv5NDr9"
                    + "fSGlQjuhrmY32G7t2p6UjMVtYKQ1lro86oryACgDWQI+t7yvEiBJWYG/0OwwsWRoN1nw65SVwLpA7ywAc0zVtaQwmEU202/GvhtY"
                    + "ZVVbmhTrlqU/CJxta5TyTLdq1vboBl7zIoUSBCMDt8gLm4U8AdZNEi8BnwUkFG4Ds6xirA3kMFkAdOmXc5BdwCPAjoAo47x6EXCu"
                    + "8Ws5z4za3eBra+seS5GkU/VkTjyuqPv2mDVAFxKLZosft6Dq6607G/mxP4+axJWYh4BlwB+BCUUnjUe1F1uUKQJRXkWTo40tNHrQ"
                    + "RwIJfJK180a7NfIC6Cq6IvAmjYc0oaqeYa2SQp4SxGvJHQees95gWlqGdc4WA5cAlbzrYhervzV+DFX1eBoHL07O+4m78+oO4AOr"
                    + "beqBm7sMmJU3wMhLKA4BT9N4UJ3WSIosqZ3ZSgu4neFS/N8sodifYI+RF5fHxYHAjrSZeTvq6bDydXVAh6LunKQ6SObhFtgP/JCS"
                    + "nYTEate8PGpevYmB28HybHczVt/2DlLTStKnksbZmas4g2N6/uGynoRDbf8dLhsp6XE7ZNbnFdu9kr6TdHlGx/Pwjue5teZK2mFH"
                    + "8tyoSNom6Rp3PA9Jo63y77HTaDVJX0maar+XMgDXfIbQgZwlabvXyfhG0rVO2v8Clwy3cjkNWvAAAAAASUVORK5CYII="},
            {"C",
                    "iVBORw0KGgoAAAANSUhEUgAAACgAAABCCAYAAAAlkZRRAAAD6UlEQVR42u2aMYgdVRSGv7lzd1+iyRKMYgyC2bgJCkrS2NmIoIKI"
                    + "KBLQxs7KQsVCsUiCKURTCAq2KbQygo2ghWksFLVQUTFRJCGiRjExsovJvpn5Lebc9ebFvJl5+3ZmkTkw7OPNzsz3zvnPuXfuPYkk"
                    + "IkuBWWAZ2A7ss+MWYDOQMD0bAt8C7wJHgF+AGeACkIV/SiLA1P5uBB4GngR2AFcDGwDH9O1vYAn4GTgMHAUKIF+BlIQkLymVtEnS"
                    + "YUlLat8WJb0kac5YvCQCnJc0kPSCpKFdkEsqWgArJGX2eWgMGwIXBuYlPSbpbATXtoVnnpX0iCQnaRBCvEvSFx3Cxd6UpE8l3Swp"
                    + "cZa1jwJ7AE05UycxAXcA9wOJA7YCD0QnuwRMjAHgQeBaD9wIXFfj4mLKMFVlawHY5oHbrfZR4T3Xohcx6e3yRjpTQxcfAT8AA/Om"
                    + "Jnh4aiPFPHBXhUM8MO+BLWO8EzR5HHgO+MQAlycU/8AA7zTI+TG6T4DU2bBSZX8Af5mnww0nOWShO2djb6X5hrrILEzFKkpIZhGr"
                    + "o+nEN7jx6DEpIDU1LGDJdVDrnEnmzAj0KFwOHHcNQ7xayw3wV+D9//BoAEtsrvhlE8BkCrBFpOO3gNcjPYZneOAn4BBwxncQ4sKA"
                    + "FoH9NmG9F5izWfYp4A3gGOC6AAyQqZWu582bNxn016bRFCi6Aoz1OAt8Y2AhiWZMCuoSMHhyGL0PxbVSTQr1Ws//sq5nKGs2J+sB"
                    + "e8AesAfsAXvAHrAH7AF7wDYsudLrbO/B/wNgEr08XWZ+nQC6CDRsha0LQGcMGf+uOYbv8q5f3IPXlinXyW+zz58Dv9Hx0oczj80B"
                    + "zwD3Adfbue+AV4EP6GjxKCy3XQUcAJ4eOb8D2A08ARzrIovDwtBDwFOWvfECZgbsBA5iGzltWmrivwa4xzRYjJS71EAXgL2ug/Dm"
                    + "lO0GCyN1cLT0eGCPb1ivGHPTJmNu3W2I1DccTWaqBvcagKHu1dmtyr3FvOph2yi3zJYpGyy0Cg8OrbxsrXOBB/7kytthSZT6B4CT"
                    + "BphPCOfsR+6kbHUZJxcBmQe+t19VdfO7W06oIXDSU26Y1PFI2xvaOXDCAz8CJ4Ab1tnU7DPgtAN+p+z40bh5WUsWb429B5wLg/aH"
                    + "5sVkHQAmwFfAx1jXx6yF+WXLaLcGeqOmxh1wHniFcpd/NsBkwJuUDV5ZNCSpJa8FuIvmqHdWEmWkuWyzNXgtdtRc9qKkjcaSSrqs"
                    + "Pc+bBvYBz1pmb7LivBYhvUC523kKeM2S9WJUZi4BDJADC/N24HHKXsJbqW5daRrW86azo8DbwOlorrhSl/8BGqzZcl0Yz3gAAAAA"
                    + "SUVORK5CYII="},
            {"F",
                    "iVBORw0KGgoAAAANSUhEUgAAACgAAABCCAYAAAAlkZRRAAACnElEQVR42u2aPWsUURSGn5m5WUmMlWAlaCFWEQRBC0UE/R/+Bv0N"
                    + "WghWFopFyiCkEhs7URCSCKKgIiIGET/bqIHsZua1yLlwXdxkZndmdoR74LDsMjs893zdcy4XST1JSDovaU1SX1Ku9qWQtC3pvqQj"
                    + "xtRDUiLpnKRnkramBOcll7Qp6YFBJkg6LOmJuidLkvY74AKwABTsSMp0xXNcAk474ASQGJiYviSBnkyBAx2w2ijQ2TQwadekAL50"
                    + "xa3/isENYNWV/JNaWoiAzCCXgHVXMXDbkE3gNnALKFyJFSXAB+Ap4Gx1ash6A2AVWAZ+AllZC34FrgPrlvF5Q1nr3+vM1XlZwBlg"
                    + "NijkTWV+zz5zD1slSfIgWYoGM/iv8HFjxInaLE1d3EEiYASMgBEwAnYMcNdWzlVYiAu6jDq3ul339rKABdAP2qG8Jsv5fd33mcW4"
                    + "gAlwxrSuhtVZ9/wSeD8EXRrQx8YCcKNm9/oG9TtwB1gEtof7zbIWnDdtQg4B14BfwD1j6ntDpBWDuQnNgYPAFeCoWdGNU2aShtTP"
                    + "5seBi/Y982xdqoNzwDFgX1gXuwCooJRtDSdhV7Y6Wex9CxOk6tDU1KBUWMy9Ax4HJaaoCtjU0UdmJeYm8NZm435VC/42rbtQA/yw"
                    + "Qr1svw2quNifzbwG7gZ1q85O6jnwJig3Yw3uObBie2ZmAV2nzNg7NUm71TO4ututwtw6UbvlXevjY9BAsZ4IsK1iHWeSCBgBI2AE"
                    + "jIARMAJGwAj4/wEmJZ9LpwmoPdrxDZuL93q2EcA1RpwP22+Jza2fbaLL2wZ8CLwa4cKMnXPkR3Yc0boFkZRKOitpxe7v+bt8A0mf"
                    + "JF2VNC8ps2dpUwkuOJ6S9CK4x/dR0mVJblpwkvgD1mlxVSjWlSsAAAAASUVORK5CYII="},
            {"L",
                    "iVBORw0KGgoAAAANSUhEUgAAACQAAABCCAYAAAA/rXTfAAABgElEQVR42u3aTUocQRiH8ad7OuosJAq5gGDWOUAuoOAVBK+QCyS7"
                    + "gLmDuBBD7jCLLFxnkWW2szAbQUFC4sf048J3oBkDM8aZtgPvH4qG7uriVx/UpgoVta9+Un97nz/qZ3Ujvr+I58IL6qr6Ub1Q6wDV"
                    + "6qU6UN9E5aot0Af1VwMymS/qWoxSrw3QeUAmMaMoQ3VbLdTlRYMqYI2/p4jyCthsvFtoSsApdVaAPi2lnKHXzoCeK6hTSVCCEpSg"
                    + "BCUoQQlKUIISlKAEJShBCUpQghKUoAQlKEEJ+p9B1SPxxZw78eCkqfoHTDknSLOtW2A0K0jgKn6oozw1RbRz0+hwDxgV6rTDuWtg"
                    + "AJzFT/WcpquOkfkBHAM/gWoWUBs5At4Dw+oRvVlExmtpN6bvXRdGqI41dQrsdWEfGh/H94GtLm2MS8DrLoFugfOysRf4zOvoBjgp"
                    + "gcPYa4pnRJXAEPiK+lLdj+s5I9vPtfpd3VF741sp6+qBetUSonkb55v6NhxLdwCLGOEhiMGFAAAAAElFTkSuQmCC"},
            {"M",
                    "iVBORw0KGgoAAAANSUhEUgAAADIAAABCCAYAAAAVHYVwAAAGxUlEQVR42sWbyY8VVRjFf1WvacBOEGUQVMCmZVIRBadojK5cuNHE"
                    + "hTs3bt27ceHWP8CNf4GJcatBXUFAnAkO0GpjFEWRoRGaphneOy44N33z8qrqVr3q7i+pvB6q7r3nfucb610kLZOUSXpV0k+6Jb3o"
                    + "kqQbkj6QtE0Skkb8DAtwhbHvkPSupFnNSy/6/FPSm5KWSxrFD70s6YQXXCSXJb0vaTyasG0QHW/smKS3JJ0rWc81SWclvS1pJZL2"
                    + "SjrWh7hM3pG0wkA6LYLIvbu5pNclXa1YU/j7RUmv58ArwBZAlEvP97wAPAjcBHLakxzoAmuBl4AV/j0ruD/zepYDb+TA40Cn5IH4"
                    + "wQzYZiDx34eVMPZN4AFgpxeZJzy3AtidA3cAIwmLCjswBmxeACBBdgMTNcft5BFl6shu4K4K1dcVAaMGkfv3rA4v1WDSp02vXosa"
                    + "6QIbDKSRgTWRe6IJ8xbABNqOW9uNgGQNJsxslMtboFcWPf+QPWht2xvGfe4BNrUERPY+OyJ7YbGAPAxsbWGcED+2Ao8NM0jTHVxj"
                    + "fz+sGw4OZwewdzGBxO5yp+OKGgLJIhpN2OYajZUPye1nvZNydtBUG2tMVZZCI9hz3W+ONxmrYyATwFNLASSLnt/uFEc13XkWBcJN"
                    + "keNYEhsJvn9dYpLXD6Rnt7s30k62FNQKedcGL6pOyhNsajPwTBs1wLAy7qtu3hUS1nXD0qoNr4Wp8aTdMIneK4uKtXFn0kuqkcDp"
                    + "fcD6GkDCvLfbW40OYx+pQHq+ymQrsNEVXpZoH7Im9rQwfxKQG8D1ins29JW/KSVqqM/vrbj3utfQikZmKlKMlabXqgQ3nEfPBtdd"
                    + "tZFDAVE04ZkE9U64/q9K68Ocq+12i3K1MP+1FJeeJ/L53wR6bQHujoBkFbS6q8Lthuf/SrG7PGGgDnDBO1NW9GwGHonSlKyi7TOe"
                    + "ED+uA39EnlDDaCQHLlkrZXYyCjxqO+kVjB2AdFwCrK+g9VXP2xmWWmHAGWAyAfQ2U6bIDYdoPuZksyq/OgtMtwEk9hwnEyrBncx3"
                    + "CQeNH2i1JbH+ON42kC7wAzBXwNVAr3WuUcpsTgaxJ8E+jwKX205RJu2GSfBeqwa44azPMaxIcLuTKTGkDpDMbvD3hHuftDfqDQAS"
                    + "3G5KE+60WdAqkBy4CPyYYCe77Fr77wuGvi2x/vgJ+G2ApoYGMgN8CVypsJPlruPjrmQMaBO3Wq5VleOk5+y0Ta0u8C1wLuH+Zxzl"
                    + "43gSfr6/xO2GzbkEfFUSjxoDCe2eM8D3iUAmIjsJ2tno5LJKLphayY2/us2Cy9ZKt6+51i9rrBGiSN6zNlLs44TBtF4hhtR8zhqZ"
                    + "q0hXMgfGZX0U2ukahAJahYB5yM6l0sibaCT3NZUYpJ5j/hWdHFv2JCzuCvA5MLtQQHqmyD/2XlXyGHBfRMNNid3EC8Df1GzB1gEi"
                    + "X2eBTxLsZMwxo+N719tGquQ751f5QgLBHD4e1SdldvK086+ec7Cxim5MDzhgF7+gb6zChKdd8FTJPpe/dwLPU/229hLwNQ3egjUB"
                    + "kjueHEnw8xvdu1rrRkOVTHmTar9tbgrkInAwakgUTbrK5e8uG3tV2n4EOF9hewNlpCG1BPxsKqwusZOOPdVp4LaKMbvenEtR/Fkw"
                    + "jWR9dvJjwjO7fGUV9nE+qkK1WEByx5ODVPds74nSlTL5xmNCg9fTdakVA5p14dNl/o3VoIrw9qg4ykrs41vHqJGEZuDQGqFvwSeB"
                    + "UxX3r+RWb7gsNl0zTecarqnxa4VArz8T7CR3sVUmv0XtJrFI33wIE404LzpRp24ooNWX3pSQ7i/aVzhCwXTFudFMUyO1/BrlV1os"
                    + "agXPFcD84JjSRLOhi3nSDiFbTCCDyt+pIcaZ8mbkTe2jLSAzpkbTd4BHbR8jQ1BzKCDB1886Y52uYSdxWnLCmxHbR6Mv1WQ1vEv/"
                    + "YkLVOJkQTwbJWfcAbkTj0dRrpYDJSsDkLoR+aQDkjA190Ldds7rtoGlXff07oehztsCrBIr851ypruc57ljUKehcYvpV0fRaDnzh"
                    + "m1XC5VPMf4W8N8BOrrvzMZ0Q0OL/xfVHUX417fFVEJivAodz4ENuddmL3jAdAvYXJHMywFHgGPBpRNcqmh5yE6Pnjewf+6bnPAx8"
                    + "XGACmfOz98IpgZclTUrqRicBupK+k/REwjGLZf7cLukjSXMFpwx6/t9hj5tJGvUaio5hIGm3pIM+wRDWeFPSeR+7GMVHHTJJr0k6"
                    + "FU04JenFGmdFwqRbJO0vODLRlXRA0nO+N4DIKg7GIGmfpM+i4xjT4eyIpPx/P1Lq15f89fAAAAAASUVORK5CYII="},
            {"0",
                    "iVBORw0KGgoAAAANSUhEUgAAACYAAABCCAYAAAA7WKTiAAAC1ElEQVR42u2aPWsUURSGn5m9ySbGj1gYCClEFEUCIvkDkqRIoZVo"
                    + "oQg2/gIVBAVrEbGyFUTSiYVgEWzURC21FKwU/CCCBpOYELM7r8WcKxdJsjNhspviHhjY4sye557zzr1wz0kkJeQmoAbUgT/AEHAB"
                    + "OA0cBnZSja0CH4AZ4AHw1mKnFheAJABLDQzgJHAdOADsMNiU6mwFWALmgEfAPeCrxW8AQlIiyUmqSeqRdEXSD7XXXkoakYSkLkkJ"
                    + "BuUk9Uq6LGnenBuSsi2EySQ1gxjTkg5ZouoYEJJGJc2aU7ON2cqCeJOS9kpyqYlxCLgGDABZxXpqZYk9As4A40AjNbGNAROBY7vN"
                    + "x6wDF4H+FNgDnAq2jE6AhTYCDDsr3/ECYFnFpVvPeoAJBxwEdhcoY7t0VweOORN+kaAztmP3Ak3LblFLzb8bGLUq/V8d/7sGDDgT"
                    + "/3rmX/4F3AKmLNXLJbVYC8p0F7jUwnfQFVz5oh0hXQFsGTB/Di8AHwtosNuVFOxqAKdNCD4JsreRZa7kqtkEVPhVq4wot6VFsAgW"
                    + "wSJYBItgESyCRbAIFsEiWASLYBEsgkWwCBbBIlgE2zorc2sdXolDuXv+ZI33N/R3BR0VZDi1d4pm2zfifb+7UaSSRcFqlt1mAFS2"
                    + "AZGRTyLsb+GbAT8deUdfG/whwD7gBnDeIDfTFcnIO2tjBcAWHTBL615kDThhT1WWrFFyv4AFB3whbz4Nsj0aqcvA6xT4Rj4t0upL"
                    + "Syt6Wml6BXiWAvPA0yAj6vAW9g5471cxDbyhc416n4zfwH1gLiXvP34GbgPfAwG2E8pr+wnwHHDYTE9NUl3SVUlLHRqheSHpqKTU"
                    + "j9D4+Z5UUp+kmx0YOpqSNBwOHSWSwr3KHx3j5CM1R4A+8smBKg/8VdPTPPAYuGP7qZ+cUQjmD/Vu8gGzfuAccJZ8YGRXRVAZ8Mk+"
                    + "uIfAq2Bv+3cK/QVxmleMprLv+QAAAABJRU5ErkJggg=="},
            {"1",
                    "iVBORw0KGgoAAAANSUhEUgAAABQAAABCCAYAAABXXvbnAAABe0lEQVR42u3Yv2pUQRSA8e/O3s1mW5tUthbxAdIkYBELwTpPYClp"
                    + "DOQd8jyWacKSIhqsJKTaRhBUbBTFsHv3S3Mu3CxmM+5uI8zAMHP/8Js5M9PMQe2rtXqsfje/zKL9oO6qqH3UQWBf1an/Xibqe3Vf"
                    + "rVFfql/mRnWJmY7UrQQcAkNAli8z4AnwogaeAptAtQKYgBrYSUBvRezOTFOE6ppAEzACbtYFJ+AEGEfYTSzw36q54CXwBvgYC5vu"
                    + "qVnrXMemnAKvgAPgUbwzIKL/DHgc/WoRaLTvgIvOrjfAAJgC23G8ssGmE2773IZZxcHv5YbcLvakA9gJdRYDkAveOUcdrFrmKCXW"
                    + "XApYwAIWsIAFLGABC1jAAhawgP8NWGXe6KtcMCcVUwFpEdi92v4Afj0ANsDPlPFTH/gEXC8YuM2cXOWACfgNvI2ZprlLeZs1GQNn"
                    + "RN5vUe1FfnGoHqmf1aaTnfujXqrP1ZQDoqZoa/W1+q2T6jtX9+L7xi2ez6k7oIc4AQAAAABJRU5ErkJggg=="},
            {"4",
                    "iVBORw0KGgoAAAANSUhEUgAAACYAAABCCAYAAAA7WKTiAAADiElEQVR42u2az8tVRRjHPzPnqG+9ghSVkIIIiWhpiZHgQokWtqil"
                    + "i6CFiQvBhbsI2gjSv2C4aJMIgbQV20QLXRQuRDEw0H6QIUK9JuSP971nvi18Djyc93rvOfc993gX54Fhzr13zsxnnmfmmWfmDpJW"
                    + "ScokHZb0s6SkJ/KTpLclYb8He+4kISlK+kjSTUmLBpXs+ZKk3VY47xrsgKQbDkiV5+8kbTCtZV2BReBjYCMglksC3gXeAwogAwId"
                    + "SA5sA+KQBsvPq4BNDjQ8pROtSgReNE2MkhJSdCQRmLO8rqgrsM60MAmYZhEszKrG6MF6sB6sB+vBerAebGQEOw0JK43f8il1NlbA"
                    + "CgvLn4kpg0EV9jmz+pPBNQqx8hahMmAAvAl8ArwGPAK+B84C/9rGZqkrsOjqOQh8bnClvA9sBU4CC1Z2MG1TluZbMpgvLE+WCmAe"
                    + "OAYcceaN0wYr3583bW1xe89oIMnyQ8AOYNE6E6YNVgDrgT1Oi2GI69gMvONmbZj2rEw2qFfXGM/ramyuWwFTJbW2We56Saq9h+0a"
                    + "rHUHG5y/Ktz3mWkga1BP5mZsdJpMXpt5gwGezF+lId8PamrDlx1UzFqyDJqAzQO7gLWVJaXs8XN1vDmwBnjL6nvo3MZd4HerIwNS"
                    + "kHQbeGUM5B/Af850VfMsAS9bPaO09Svw2Nrymn8AXAdOA5eArC5YV/IjcBS4EhtM8+Ryn5YN3Ib1yK2re4DPgBeazMowYsqHFdYT"
                    + "HOQ+YPssnY8Fl16NFszN0oliAOajTdOlGYGSgd2LwDc2hZObwpogTbLoq+JOAvAXcC0C54AfKpFlmCA1HUfV9zKz3CngVg78A5zg"
                    + "yXn/XuD5FQSN2RhtLQ4JHKIB3QG+As4ABEmZ+ZCXgA+A162CVFMT5V84HwI73TipysB2S7+ZEspxPWerynngsl84C8v/Br62aLTu"
                    + "mCk7tQnYP2ZAR+Aq8KW18dj9Vi7uJU/yK3p0DTUxX7Kwuo6znqs4UznLZD7iyIeEMKGhzymGhDBPk8IpoXpsoHGBoibwPa2H2f35"
                    + "WA/Wg/VgPViz0CdQ895GbKlzA4tIxjVYO2pp4xgqAveA2yO0ISvziztm0DTBktWxAHwL3Gf5XY7SfBdsQ5vX2oe2cHMpsztoayQd"
                    + "l/Snu0lVXvc6L2mbXQlbbXkYd02rLbjc4D6VtODALkp6w8qVUGPB/gdzszupyZGTigAAAABJRU5ErkJggg=="},
            {"5",
                    "iVBORw0KGgoAAAANSUhEUgAAACYAAABCCAYAAAA7WKTiAAADqElEQVR42u2az2sVVxTHP/fOPJNWa6S40FQSVxbcVAr+AUHoxh8L"
                    + "FwWhP6CrIurCfaEg1J2rVhfZCbVUdyoiuFURdKNddCON1BQ0QhPUKObNm28XORevMZGZ92beFJkD80J4M/d97vl1z7lzkbROEpLG"
                    + "JZ2TlGlZctUjYdxHkg5LSo0hMQ4kgSRnUBckLdYItFJ6kmYlHTewVJKPwUYkndLwJSjgmaQvDe4DUxQemAD2AgJyhifOfm8DcND+"
                    + "AvjwsQPYaDc6hivOFLIVGAOywOCBLYGyIXHAR8CHQJdIbRnNioCnwHMgjcHUMJQD7gL/Ap3Ak5YYJK8YCCABZoBfgUVgHdArC1aH"
                    + "H94DfgBuG+Sr8EVaUNVPgGv2vyowf25j/g7cMag8Hreoxl4BZ4Bb9ky3AlPmNunUzPfGZIuCJcBIFN6DSmKOnltWeMsCaYkZ9qKZ"
                    + "9gYAdPZ8FrkK/YLFgFoRWf1GJO8ap8mMP/QU0IK1YINEpbdr0NpNUS3GIHkszj9uwGUpiX5Xq2X9MmCZ3RuKyu6KGZeByoCXwJJB"
                    + "JdGkC4MFc40BJ6NlhD6TbAB4CvwB/GaLuIzlddEq6RtJj2vuJdeSGUm/SJqwtq0Td0llSpUqLwHbgcPAz8Cn5iIdwKUlzFFHaglu"
                    + "sd84vgceAR1vztdkHg395QHgBLAJyH0DveRqAREi/DvTXuatGej9D+BC0j0EbPbAA2C+hk6oHziAz4BdHpgFpg0qMe2FKrXqKy8Q"
                    + "bKPAF6ndPA2MW+iub9isI8BuJyloaRT4FthnmlMNGy0p8Dnw8Tvu6QIPnaR4q8BZgqsrLXSAUxZ9azUiGTDnI6d3fZRBZcucZ5YF"
                    + "StVjua34rqaIC+6RlAXrt2Iok6cK13HDKq1LF5ZtM9KCtWAtWAvWgrVg78H+mFtRTldZ/rjo8kXBXPRwJypTqmrlFAFlBXtYl0ZQ"
                    + "3irYWJN5RRWti7aaNhbVWBKZ7StgKmoeqAAsTDo3i0wV2GhZSCObHwF+ZPk18DA779W6pH/Cm7SvgZ+s2cxqjta1etXgi13grxT4"
                    + "BDhqUL0aW7ii8gK4lAI7gW1G3GReC35+D7jhTWNNnLlYzYwOOA8seGCOZrefYrCrwBXAe+A+y6+Wq3jXXRYmbBR74CZwHHgMpB54"
                    + "CFwus1xUGJ3hFdB14BjwpwXfUjimtdWOab0c0l7/kqR5SX9LOi1p0o5ojYR9fqKDbZOSLtq5rrzmc2MPJJ2VtMdgEuNw4fzYf9nX"
                    + "R8GRi28RAAAAAElFTkSuQmCC"},
            {"6",
                    "iVBORw0KGgoAAAANSUhEUgAAACYAAABCCAYAAAA7WKTiAAAEh0lEQVR42u2ay2sdVRzHPzNz7k0aTWswKQhaBC2IdGe19UGt4gsU"
                    + "ixbEhYIurBSFuhIXXRbX/gHdKIoU40J8VGtwqwtBC4qoaUFTxKqkkD6MvbnzdTG/0x6vyZ0zk8kN4v3BgZt7z+M73/N7T5BEjZFK"
                    + "cpLakg5KWlAhXUkzkm6yee2a+1NnURKA2i/plC5LLmlR0lFJ19v8bBDAEgOUSHpI0nwAqFfekjRhD5HZmuizUqpJCuTAFPAiMGF/"
                    + "J8GcHBDwOHALsGTrKh8UKwmQAV3gduAeA5AsMy8BRoAt9p3WElhmB2wG9gFjAZDlRMCGHsCNAwvZ2gncG7n3hTqgqgDLgI6x9Sww"
                    + "usI1hvIr8E0Pg40CS4CWzX0AuD9iPsBrwFdA25hunDEXWOJe062V2PKsnACOmUXmgaU2Bsxb2EXgNtOvGHkH+N5UIKeGpBG6tQRc"
                    + "CzxnOtaPrQSYBd43xU/WAlgSONTtwO7IPacD3RI1pQxYB7gKeAy4IoKtk8AHwJ9BFGgcWGaH7Qb2ROiigCPA8cASG2cstY2vBvYD"
                    + "GyMs8UfgXWDR5nVZhaQl398J7Ir0W8dM8VtmMDQNLAvYer7Ey3u2TgJvAwu2trtaYG6FmJgYU7FsvQl82RRbyzGWGgvjwDORXv53"
                    + "4FOz4FVZ4nKMeQ/v7MD7gLsi9zgCfB1YYj9LV+xvrke3cvPuB8x/lfmtE8Dr5re8Q41NDPJg9GXMh59dwM0Rm3eANywmbjS2XMQ6"
                    + "2TndAMO/fF4iKQQ4CRwGHo7It84A31ZIBBUkBD8BHwOfAGft/Iv/AGfVy4jVinsl/dan8mlS/pD0kaQ7gho0Dcu3lpVXk5KmK4Lq"
                    + "1hzh/rOS9hkOX+qBpFFDuseeYhBs+TOW7PMZSU8ZMCcp85XyNVY9DwpUKEsBczvsKkd9sN4eWfmshXg3dQPwkoXCbmp51hOBH0rW"
                    + "AZw/81EjqZMC24C761bMDQLzBfIjwJizciymqlZDAPqdkRpJU86oG13Fhk1f5yZgkwO2WrrSj7Fz5uU7Ae1VD/RxeGtJPG0BW5wF"
                    + "67QkjEwDr1jYqJPatG3tgxbyxvsYWgZMusinnwVOB6ZdVd9GbN1pY3+8LK66SN1xQZ1Z1XoVpDlp7FpXYfNwVL3KvGd9o427gcoQ"
                    + "2H8BWFS2O2RsCGwIbAhsCOz/Biw27enN+avm/0nVdS4yt/JtoqxmtZQEdUVU/hd7lZutksprVkupFTJjwJUx8x1xHeYnKd6+/VWD"
                    + "NZ+SLwI3WpOvrIbtOGDOGHF9dGPKwA1CcuDnlKLjfKGkyPB5fhOjX10BRRd8LgU+43IrvOw6mhhllnscmE+BH4Dv1tlthcXvDHA2"
                    + "NeoOm2KvZ8cHimbxDOA8vUeB94K+xCDBeRd0CjgE/AK0fLc4k7RN0udBu7O7hm3PvKcHuyDphbBBHLbTE0k7A3CDknlJL0vaYARl"
                    + "ki69gPAho0vx/zgHKNqOExSt0FbDin6e4jXPHPAqxetqEbxS9MA8uLa5jhHgVuBpio7jdQ1mIueAL0ynPzTdaltkuNRJ+htm684T"
                    + "bZmSmwAAAABJRU5ErkJggg=="},
    };
}
