package io.github.hieu.filmfilters;

/**
 * Glyphs of the Leica label typeface (rounded monoline caps and digits), cut from a reference
 * raster and thickened to match the weight of the camera's own Leica labels: white pixels with
 * the anti-aliasing in the alpha channel, as base64 PNG. Every glyph is {@link #CAP_HEIGHT} px
 * tall and sits on the same baseline. Only the characters the film names need are included.
 */
final class LeicaFont {
    private LeicaFont() {
    }

    /** Height of every glyph bitmap, in pixels. */
    static final int CAP_HEIGHT = 70;

    /** Stroke width of the typeface at {@link #CAP_HEIGHT}, in pixels (the width of "I"). */
    static final int STROKE = 14;

    /** { character, base64 PNG }. */
    static final String[][] GLYPHS = {
            {"R",
                    "iVBORw0KGgoAAAANSUhEUgAAADIAAABGCAYAAACOjMdmAAAEAElEQVR42u2bTYgcRRTHf1XdM0N0UePBaBIFFUQJicGIBz0ENXgQ"
                    + "8SCKeBA8CF7Fm6g5e/MYzEGEIIJKPOSioBK9CcqCl4BfiOJqQuIHkXxsevrvoV+5zRI3s1W1u91JP2h6Zrqr5v3fR71Xr6qcJAAH"
                    + "FEAF3A+8CuwFNtmz9aYKWADmgU+B94DjwAQQcMHu/5GT5IDSHj4JvAbsoht0GvgH+Ab4wK7Txu+ivdMAkjSShKSnJZ1SQ7W6R+ck"
                    + "HZa0x/idSHL2GezDg5IWrEHVIeZru6at3+Yl7TO+gxLwZncvADcBU/OVrpCzy5sJ1cBu4E1gn7nDGHAeuAW42150dJcCoClwG/A6"
                    + "sNPAjTxwMzDXQt91KgzMHuAlc3zngavoH3m7Pw48BCwGValnQJyZ1PXAM8DY9xBEmwTcC+zwPQYRfHozsLfPQAJNgHvKDKrVGjjx"
                    + "rBrB4si2MpN6N9rEJmWkFhxwHvgcOGFjuRKZAbjPgt1qg7NSNDICPgLeMPVOEzRaATuAt5cJa1bB1mUGRwvMKFEbE9NsFOVy9lin"
                    + "V0sIdYp5lhmdbkMDq8+gjVwC0EYBWauU47IAwgBkALJ22eyGDL9hHu0j+3ItYYZYsu5AnKUltaUYsSkK1nYrsG3Z7+sC5AJwo001"
                    + "JwlDpwduAB4DthBXzanKBCk64FnguQz+cZ1VR1abLDoT6IkUjYxNkjmDYYyznwfmcySNOUetGOB/AJ+U6/zna0E/At/1vYpSA18A"
                    + "Z/oKpDYgx4D3sZJp30g2ZP8FHAB+Aoo+m5YskLq+Jo3BNzYDz1tGUPVVI6FmvZNm3bPuu2mNgUeBa/ocEAPdDtzZpYBYE1f73QQ8"
                    + "kAJk0YbAIoMQro1IGgNNgLtSar8CDgFfR6bxoY/S0veHaZbRVksjEqvxY5ptFe8aM7ETq7Dr4kvgDmD7jJoJzwtgrkx09CJhmtue"
                    + "pxfAnzSV/e0xJpZj1Kpb093Y6XbVkm4UD12KI0kl2KGuNQAZgAxABiADkAHIAGQAcmUAuWzW2ZPWED3d2SLrUjVSJHSSUwjTyOky"
                    + "WBH7TIJNu0y+4YC/gXOR7SsP/EJztGE1MzRZm4UMQGqziuPArxGDg4DfPfAzzYGTWTePBQl+D3zFUlE5tYhxFjjK7LuFguBPAh96"
                    + "msXEg8BvLO07X6mxNxN4C/h2hjaz+kcBHAE+Y2kzvy6hDQccBo4gaWxnMJ6SdHKFgzB160DKfklzkgpJPpzhSLxKu++2MyL6H17a"
                    + "39+RtFVSgTESwDwh6dgKB1NOSXpZ0tX2x0UmEBcD87GkxRX4OGAgvKRR6KAN5hFJRyWdbUmgkvSDpBdbIMrMILCjRqHfWyUdNCup"
                    + "l/HxiqQtAYQk/gUl2jtOUE6gQAAAAABJRU5ErkJggg=="},
            {"P",
                    "iVBORw0KGgoAAAANSUhEUgAAADIAAABGCAYAAACOjMdmAAADV0lEQVR42u2bu4sUQRCHv3ncnucDgwMxMlAMRFTwgYGCqKCCZgqX"
                    + "+BcYCCaaqLEiGooaiIaCBgeCBoKRD0xEEAU5QZQTD07Bx3GvnfkZTDWucuq53bs3c05Bs7uz2zP9TVVNV9V2R5IAIiAFpoFtwElg"
                    + "B7DAvuu2TAPDwDPgHnADGAV6gRyY+rVDJMlBNIGDwClgPeWQr8A3A7oF3LTPCTD50y8lpZKQNCDpowrJVT4Zl3Rb0nZJkaRee0US"
                    + "2Jt9kkasQ7NEg8+tZS3HnkvabRANBxMDfcARYBmQmdrKIpG1GJCNby1wGdhlvtQAohhYbV9qjhz7X6ASg1kFnAc2m/M3YmC5oyo5"
                    + "iBMHswE4Zp+juGSmNFtxprYH2AlMuANVk8jG3Q8cBpbEVFsEbAE2VhnE+fRSYFPVNQLQA6xLA6g2D/i0a+fGNoAVaQD1JnNoWg5+"
                    + "QeqhiQh4CLw29YZ4+m0FVrYzOfuCPACOW3jd9DCnJrAGuN4GiIDM17QaNpDIQyOy1utxYwnl7ApgWl7nSAM6n7rcz/txV9rgqwap"
                    + "QWqQ/wAkRKzlJsTU42Z61wt8QTKbEDNrPpPqtE/wmPp0tlRzAFhog/HNK/Jua8SBHAD2B8hHItNoX5u+G/uaVn9Z0t4QQWMnkqV/"
                    + "SSUy4HPaxQt3SiaBF/NhHpkEHlcZxJn1O+BRXHGIHLgLDFcZJAaeANewInbVJDeIj8AF4A2QxhWFGAfOAYMuIkgrZEo5RTFwAjgL"
                    + "XKQoI0UhQNQlp3b/47wHzphfjDmIUGF8R0MPe/0G3AGu2lMqMbgsRBgvc7ioAwPPzQ9GgZc2+EHgC0VRsNkSKcsHxMU5t+0iIcL4"
                    + "VpAMGAFemTlNmVP3MMOqhxAgnyiWV/TQfu33b9ljMoMWgmeIScvFOhEJyzLHv547ZO23yRxKXQ6qQWqQGqQGqUFqkBqkBqlBapBK"
                    + "gvjk3FGZQOL5opFxj/5ZmUDeUhS7ZrvMyBUepoGhspiXA3nK7BePOZAXFEsBozJoJqaoE10BPvBj3fmfIGKK+uslg0lKYWKSkllu"
                    + "hHHHJiSdlrTY+sZuM8pcNmwjSY8dOCTp5W82o8hAT0haZDuBkjJAtO7oaYXZK+m+pLEWiKakIUlHJfUZRFoWCEl8B9PZCisiy6HS"
                    + "AAAAAElFTkSuQmCC"},
            {"K",
                    "iVBORw0KGgoAAAANSUhEUgAAADAAAABGCAYAAACKeRdbAAAGbklEQVR42u2bW6hUVRjHf3vPzDlpR4mMMDMiutGDUmLYk1E9hJUU"
                    + "kSZZakoQQRSVqVmahZegko5vqYUQXSQrirKiGxWSGYYVpNFN0kQ0Kz3h8ZzZ+9/DfCsXw5njzF57jz60YNhn5uy91vdf6/9d1vft"
                    + "haRIUqckJE2Q9J6kw5JS+/RI+kTSNEkVSR2SSnZ/6CfyPhX7baSkZyXtldRvMiSS9klaJelsG79TEngCTZG0TQO3RNLPkhZK6soJ"
                    + "RL3wsaTTJL3sCe6a+7tX0gseiA7swZskHfCEHawtlTTEQEQ5ACiZDKdKeqlOYA0AQpI2SDpLUhlJkyTttH9UBxHcATsiaaqksrfs"
                    + "WQGUrI/hktZ646QNZEg9ObolDUXSliZn3r/nY0mjTYCsVIqNxx2SHvKES5uQIbVJnxIDlwACYo7dIruOBy4CkiafG6iVgX7gSuA+"
                    + "6zvyxhhMhggYAdxQBkotDBoZ2C7gjDpQrQpfBS4AHjNh1GRfkXcdlXX2CJj5kg0+FJgPXAqkGSYiAuIQAMrwTARU7DofuC2D8PKu"
                    + "vSEAsrQKcAS4GrjfVjHKSMMq8H3cZuFTYBzwKHBSC7wfaAV+ATa0C0DJBj4ZeNhApAHC9wPPAZvbASD2LN3dwHUZZx7P3G8E1gGl"
                    + "ogFEZjL7TPAF9j2L+U1N+C3APKAXSOKChe+wgccDS8x0ZuV9DOy1frbb731xwbxPgGGmtGMDeX8YWAG8bxPTF+KMmnIyNvidwKRA"
                    + "3kfAO8Bq+y1xwOICqVMFphnv40Def2XWq9cDQFEAnNKOMeoMC6BODPxuTm+70bI/j3jmWPb+TOBx4LwWIt1GvF8JfA501gufNwDf"
                    + "3t8DTM6B968Y70tGSRUBIKrj/SxTXGXsz/H+a2AZcND6SvIMiRvxfpxRpyvj7DvhdwP3Aj9aDFXNO6b3LYozl6PNyYwM4H1kvH8K"
                    + "+MxWtb+ITclA/TwIXBMYYQpYA6z1Zl5FAXDcT4BbgdsDeO9AbwOeBHqMTklR20LHVwGXAUsD4hyf9w8Au2z2k2aVL8vMp8CFwM3A"
                    + "zBx43ws8AXzk2fumVjOSpIyzf8AGP8XbGrYqvBu7G1hk282q9U+RAPJojjrfANdaqKzBTGbeOqBApXVxzjxgj+dtaReArNkEn/dL"
                    + "gHdNF4+0MzkVmktKbVP+YrP2/kTRAcf7zcBUM51RFuocjxVwwv8KzDV7XwoR/nhQCM/D5rLy/1Mog+UStYz0XLM+Ea2l+I87AHed"
                    + "A9xiYUM5o0kOopAyZhp8Ku21zMWnHM1cn9A6MBCILz19oF2hRArst09WJxR7+jDfwvG4VZniAC6vBu6y5Y8CzeIsC8v7WtYHtd5c"
                    + "GXSmlUsnStrVQqm2Uel2t6SrrPza0Wy5NrTIFwGbLO3Xk3ElYqPkKNtOjjZalos2oy6crlhQ9nxWU+gBH2tpxC6OJojb4gdSS4W8"
                    + "nXEVfP9whyUImvIPeZRZXeJ2p8X3O719c5ZVGGKrMNGUulL0Cshmq4NaGvwR4B/PTGbRh7NsRc8xfai0I5SoevrQnWEF6kGMBxZS"
                    + "K8c2lDVPAPKEfgb4MMA/uOemAbNtckoD6UPewZwLD/YDi4GfAvQBanXlBcDlRtNK0SvgQJSBLyzXcyijPkSef1gBnDuQPhSxAqk3"
                    + "0Hrg6UBnmQITqJWrhtfvH4rYD7hiRGKDdwOv56APU6gVThLfPxS5oXFvcx0wfdge6OQ6qb2qcIXtGyrt2JFVzT98R+3NrMMBIJw+"
                    + "LKdWPEyASju2lC4keNWCtTRg/+D8w3Jq5du27InlKfdK4C1PmKz6MBmYDvS3a1Of2OAHjUpbA0yrTB9mARe3MyuRmPnbaiAOBSr1"
                    + "+cCMPKqUWeKljaYPqnOCrYxbAca0G4C81OJKapX4EP/QGQdal5B4qceo9G1GfQBIY1qrtLjixD5v46IAKv1gIcKfLfTlx1x7Ymqv"
                    + "LzYbMbqHNwE7OPpWVivBng+iBLzp6UMzk+nu+QN4A0lzJP3VRFok8Q4hXG/pj0rgIQj3+v0wSWtafP1+laQhrpPZ3gEIHePwwTI7"
                    + "AFEJPADhzhCUTYYRktY3GLP++2t2ACLGm8XpknZI6msAYpekRXYEpWwrkNcZmrJdT7eDEIcayPC3pHV2BCWWVME7w1KSdKOkD+xG"
                    + "dwioz87WzLD78hK+EYjhkhZL+s2jU2Lfl9nBi/8ODf0Lva7xZgDJqTAAAAAASUVORK5CYII="},
            {"C",
                    "iVBORw0KGgoAAAANSUhEUgAAADIAAABGCAYAAACOjMdmAAAEkUlEQVR42u3bS4gcVRSA4a+qayYTNSaoaAYl0Y2JICgY3PjYRCQg"
                    + "rnwFgxsJCj4WulCELBR3bkREEckkO9+6EcEHbkQxa2OULGKMiGCMio/Jc7qvizqVVNqZJFM9GaulD/Smp6b6/Pfcc+65556bpZTM"
                    + "IgVyHMMS3ISNuAGX4Rxkzp7M4Cd8gXfwCY5iDIfj7ydJNgtIEUrmWI97cT0uCYDC4smf+AM78RI+xngM8LFTgYyhG0o/iU24SDvk"
                    + "ALbi5bBWHpYpAVJK1WcspZSnlFamlKbSCenGp5cWX3rxmal9N5VSmgxdxyr9K4iiBrG19pJuao/UB3NbwHRCdxXEeEppeUrpxdoo"
                    + "9FL7pK7b8ymlidA9z9GJiHAfNteiUaZ9UtfpAdwfTj9Whdjr8DAm0GspRB0mRQR9CFegm4f334O1AZFrv1Qwa3AnejnOwzrDJRVI"
                    + "gVuwLMfqWK2HVS7HxTmuDatouW/M5fhLsaYIizRNO3oLrFwT/yywsgjvzxfxh8+G5EWDUU1h1t/xOf4acEpmkd8tw81YUfuNeZkl"
                    + "NVSgi9fwZryn6TTrxFq2KbYLjQajGHAkl/SFw0HeNdHQsglHBp3jaQE+1Xv24++GM2NfPuAoZgsEleFrfDePaFg98z12DWqRhVh3"
                    + "uuFje/FeLRqm08yETvzv+9jfhvDZi51eju14oTZA3T7L9WpJbQ+vYhuKQjukSlansSUAHo9Rn20GpNj2Po3fqnVEi2A6USV5Bt/g"
                    + "tshwz42/Hww/+ghvxz4+x0ybQKqp1MEhTIXPrMWFAXIAe/BrAFQFCG0DqfvFeGQNX86ygI7HczP1lb2N0ovtdz6LjikygdSforRZ"
                    + "emea+gzDttYIZAQyAhmBjEBGICOQEcgIZARyJgWObJhB+gGy/9PUyoYNZN71smIILJD3gfVivz4UIJmynaRSutcHNtb3fStBOqHs"
                    + "EWU9aw1Wha4H8C1+jueqsmnrQKrK4hLcpSzQXY0LwhrT2K2s976rPGzqoFu0EGICT+GJOQb6SmwIwGfDSq0pmVbTaamynvtYfN81"
                    + "+/HFGB5Vnn9uwS95Sxy7UBbd7g6IqvreqUWtrObsVXFuMx4UnQ9tSJO6mMQdtRCbnQa+ilgbMFks8Mg2nVbHcA2umsdCXT2zCuuK"
                    + "BRjN7EwTu9PIaixv8H8FVg0CkpRnGfVzwKbvoTxKaHoynIowbdZwSmyMWJ8b7HiaE/0wTaRXNEgcK+gVuL1NSeMhzbsW2tBUk6qV"
                    + "fa/yUGVYd5gz+DnHVzWQZHik0vUwdufKU9IfhrjusAc/5sr+8w/DRNmQWKUK9weVlwCmq77fN7BrCEF24gMcb2Deh7eGxCopgswh"
                    + "vK7sKupUDcyFsqdjm5PbJNrq4Cl03X48gexr8p+MBvq2Nvmnvib/rN7kX792kcUD9WsXM/9h0381mDM1mFf6IVJKc16EuVTZd75Z"
                    + "uy7CTOG52l79eAvHXFeTxmORvBWPxP54Oc5fRMWPxmK3HzvCsT+NtCjpu2eVneKy2IQTl8XWR1XjxtjJFWfZoadjkd4R0fSzgKpq"
                    + "Xf+6LPYPWr3byYybHyAAAAAASUVORK5CYII="},
            {"F",
                    "iVBORw0KGgoAAAANSUhEUgAAADIAAABGCAYAAACOjMdmAAADcUlEQVR42u2bPWsUURSGn5mdxJgoVoKg4geKP0CxsrAQ/ANaWakg"
                    + "QhCrlFZio1gIdiJWomAhYiGCmMJCJH4gAQ1BMYggNhqNSUQz81rMuexVwkYzc3dnwhy4JJvM7pznnnPv+Zi9SEJSIimW1C9pWNJL"
                    + "SXOSMlVL5iSNSjpoeveZ3iCpZS/WSDor6ZOkVNWWSUlHJUVmhMiBrJV00bswqzCE021W0gmzTAv7ZcQuyGpgDUlasJ8zkg5JIgZ2"
                    + "AIeBCMiAmOpLC0iBNcBJYF0M7AM22wV1gMDTVcBOYE8MbAUS6ikR0A9si4EB+wM1humLbV3UWVJgOjE/q6M4vaeB8bjGlpC51WPg"
                    + "dVJTgMy24LfAFSAtCpL1aHG3gClgGHgCJEVBeuGaGXAfOA+MWuhYlkWcb34BHgEzNkPqgktNA2PAQ+A90Ge7lpKCM3MTuGFBKQ3o"
                    + "Si5MLHie4CAyCkb0GFjtBVMFdicXxV3sWPDvWcZilzdCxouOk5UEumFPMsgVIQ1IA9KAdJYqJ43R/+yIRUDkRd04wKREXqa7JEzR"
                    + "yO5ShDRQiiLL41yKkoUA+QVsAY5Y3qMSLRED88AkMAHMerpmZYE43x20eqBMCP/zU+Az8AK4Bjyw/0WL3a+IRYZshJT1wC5gLzAC"
                    + "3PbcTGXuWt2oQQRsBy6Zle7Q7jSWFkeiwCP2lN5kVtnobQK1C4jOnXbT7lPXEsRtAAPAAfLmtXz965iibABW/b0+6wjyc7FYUicQ"
                    + "514fLRhHdQSRl02MWaT/IzDWBcR1UZ4Dt7xksrQuiroE0QI+AOfI+70J7R5XKSDdeEDkIE4BdxeL6kVBZoHvAZJGvxb5BjwFrlrS"
                    + "2Fosz1ouiCuoZoHLwLtAGXBE3nF/ZkD9/NVdLMsifeSN5Ov2OVkgq7QM4lfIUrflJXah1kvqWSFIy9SvqbOlZqyEDSVYzd7tYLhk"
                    + "erwipAFpQBqQBqQBaUAakAakAWlAgoNEVQKJCrw3XgkgP4C5qoAk5L3UhWVUbJ/IH1RGVOArtzF5v2jKXmf/CBKRt2kmaPeaeg7y"
                    + "ivwrfe7BSSelXPvyK/mDyXnapxx6XNnnp3nWSrrgnc9IOxxAmZF0WtKgd6SJXg88RYYknZH0ucPhk3FJxyQN2PsqAeFAHExk47ik"
                    + "N2YVd8JnWtI9Sfu96ysDIYnf/sKOV5zFqxoAAAAASUVORK5CYII="},
            {"0",
                    "iVBORw0KGgoAAAANSUhEUgAAADAAAABGCAYAAACKeRdbAAADUklEQVR42u2bvWsUQRjGfzu3d6eJxBi/CkkhARErBfEjkBAExUaU"
                    + "NP4FNjZaKIj+A2IjamOrhVj4USiiqAiCYKNWghhDsBEVPzFIkrvbx2LfDXtHBN1N7vZgXlgWsjt37++dmecmzDOBJICA5nBAFagD"
                    + "ITACHAaGgUFg+QJtFiPqwAfgBfAQuA18Bsr2fLa1QSBpoeQrwBywCzgK7ATWAj0GtNTxy67XwF3gOvANKFlemn9TUiDJ2RVKKknq"
                    + "k3Rc0pQ6HzVJTyTtt1wrdkcSpJIv2X1A0jlJM/YBdUkNSVEbk47saqT+9l7SIUu80gpQklSWtEbS+VTDRgF6ILIiStInSQct+aqk"
                    + "IAHosfsZezlqc8X/JRKId5JGbLhXHbDMZv8B4JhNFC2RyuSJEtAAhoBTQB8QOHvYD5w0pYlMiYoYzoo7BowDcw6oAXuBrQWtfJPs"
                    + "W449BtCbaP4+oLcLANIQW4AxBwwA2+iuCIAVwB4HbLQ5QBdUPx0VYCgEVuaYtGr6Wc9X0eA/3sXWR4OhSZPa8MVLIav9oclmlsoH"
                    + "wHNgwqqhjAWoA9uBzRlExIU5hk4APANO2HhsZFST1cC1jAC5l8bl1DBSBoDI4MtZC5kXQC3VzCIAUR4hWKx/TvKoUS4VK+qaxwN4"
                    + "AA/gATyAB/AAHsADeAAP4AE8gAfwAB7AA3gAD+ABPIAH8AAe4K+xmPaxoBsBAuvFLPvFQUv7jgDUiLdXw4wAiVumlrWAYcZ5kCQ7"
                    + "DFwl20Z30gM1YEOGYRiQY6M7+aLddnViHkXATGgVyLNF2m6zRxIN4EsIfKW7zB6JHSECph2xtfdn6mG3xCww6YDvwMsu+/0SsTX5"
                    + "jiP2Ij8ApsnmeaBDQ+gN8NSZBD62Xig6QJL8DHArmQPYMLpsc8GRzQTVToD7wA2g4owmNKJLJk9F7ImGFXcCODsvPGakTgzgqyRd"
                    + "LLD5+6OkcbPfVxP3enKGILT7gKQLqUadsN9rAfv9W0sey5VWgATCSVon6bSkiYL0wD1Jo5Zb0wGIwM7QpNchJZsTNWJH7xHiMzTr"
                    + "iR3j7TiC8tt0fpL4HM0V4IflNpuen2mA1mV2hdgSGQE7iA8BjQKbiM3XSxGRLW1eAY+Am8AU8REBmeA0xR8Ye4xqPVJ8NAAAAABJ"
                    + "RU5ErkJggg=="},
            {"1",
                    "iVBORw0KGgoAAAANSUhEUgAAABoAAABGCAYAAADSBoRCAAAB6klEQVR42u2Zu2oVURSGv9kzJoKCjWAS9A0SCxEfQESE4GvY+QQi"
                    + "4kuoacRGwUpMIwgWipVgZyPYaKMgEvGGenLms3AfWMghOTPnUshesBhm3769/30p1kJFPahW6hn1ofpVbe1urfpevaoeymM3KqjL"
                    + "aq1uqi/VodPbd/WGupphNWpSL6gfwqymsdj/boYl1OPq01wxcDbWBmWuqyTgMnAKEKiZjVX5K3AR2GiATWA5VDJDWAWcAM4l4Mgc"
                    + "INEaYCXNUK69zMRizAR8XAQpAfeBb/mEOE/QLWA7H4h2DjBHoB3gGvA4H4wRcJw7zYoa4C1wCbgD/Mrl47z3NajUKsMGwFHgLHAa"
                    + "WAUOhL0TOAmsd5CsyoptjUAE2QbhojVZsgGwBtwGzodBJgY1oWCY5VnK/wK7YcCl7L2fB8ImD7NXAZAC2GlB45ZteBydxT1iUS9D"
                    + "ARVQARVQARVQARVQARVQARVQARVQARXQfw3qE6+rgCp17NBXgdSl4y7wowdE4GeasGENfAJe9QC1wLs0YUPyah4BX8IE9pug/I2r"
                    + "P08dZlUDL4An7B+6jrG+beA1OWM1iTc5c7ahPgt5or0SW/fUY6NEVRev83ddfRCSUf/CdtSb6kpMvfWFralb6ucA+q2+Ua+oh2My"
                    + "8Q+2eN+XP3TcTQAAAABJRU5ErkJggg=="},
            {"4",
                    "iVBORw0KGgoAAAANSUhEUgAAADAAAABGCAYAAACKeRdbAAAEe0lEQVR42u2bz4scVRDHP+/NzG52NRsRcQmSKJr1B4noKUdRBPGg"
                    + "IqIHFfwH9OYtILmIJ73oQaPoKSdRD+LBgIkRUeNBiKCH6EGiLBqNkeCiyU53fz1sPSia3t3p6Zn0DE7BY7rpfq+/36r36lVVTyMJ"
                    + "SfOSupJulvSipB8l9SUV1v6U9JZdx+4Ndtxq8+D3SToq6byqpS/pI0n7rXNnEkggqSfpFkkfOrBFCbw//1LSAWeJ1gncIOltA5dX"
                    + "gPckMjs+JmmvWaFVEhF4AngQEBsSqJYARKAAHgAeBnKgs0WfsUsE7gd2GYjtgPjr9wGLRii2RSICe02L1ARxvRHPaFEisOQI1BEZ"
                    + "YbnzVgh0huyrtkCXCUy1xEnQ4v/eAjMCMwIzAjMCMwIzAjMCbUn3Cj8vVES0E08gbpF2yhIiTSqBrgHP7Ng/r+9y6mLY/KI7ZvA5"
                    + "MG9FgHuBfQb4MvAdcAI45SxV1CYh6aykdQ0uuf2elLTbFbl8uSOdr0h6U9LqJmWaM5KelbQgKQ5TLBuHBTqm+RXgdat6VKWgAbgV"
                    + "eAnYAbwBXKpriTgG8AG4Bjhs4HNXAIiuBQO6BLwAPGX3xroeYpQusmML9nHgUbavGUUDvQt4Djhg/TttEEhgFoCHgKs28f1VVhOw"
                    + "H3jEWaiVnTgHls3b1PEmMiIHTQEaFNs4QokFhi81Xl2337hioaJBv1CHRHcE897P2aLheHIEOm5cbeZamxLo28BJcym2CQ08WWYt"
                    + "Lymq69ZZMQoCuW1EKyUNLgLrDSxwp9tPgoUdvwC/u9gpbXYESWeB3UCv5sN+A/6qUEKfjdL7dW5KDCq/Ahedaw0G+gJwGvgEOA78"
                    + "Y9f6TQi0IReAV4DXzDLqNlxw5XcEobQQm44ZSgv3WuCQTZ9XgayJGw3O38fScRjRmOX4qbAd/hDwGNCftpw4hSs7gaeBpdZezjW0"
                    + "vIDbgD3TWJVI66sLLMemSXXLRHZE8+X5lBJYi8DXtnNqSiyRcJ4DforAEeAHl+JNOvik/ePAagS+NRJrLiFXwzYKDfux5ILGaOCP"
                    + "pLJKR9KipOcl/a3Jly8k3W1llV6QFFwm9STwjOWnOxnuPXLS3PwQ2r9cSrKSy1wHfgY+Nc2fttitHyT5xCQD9gD3ALebr607LWTb"
                    + "/B3O5IMmMqeAj4287PlzwCrwGfCN25GzFE6Xi7BpDcxVLJ7ttvkMuAn4wOL6QQmk+46x8f+l9VIonlvr2b1ZVUKTFknHEak7ddJD"
                    + "ht3hfbISHIaUYvbLyuxukmnlNWOk4GIUNfRAyZWXIwTVTerrAglj8PkaZN5OtcwIzAjMCIwnTr9ifeOYlBKm2QL/NsjwsrYJdIA/"
                    + "gPM1N8JksTMWB4U2CKQ4ag34qqKqtlU/2CjenqBmYXhUBOTABOCo5drbBYU+RXwPOOn6DGaFEf+fP9gHFUg6KOl791J7q48q3pd0"
                    + "o2WHPXvpHQf9AGLcJD53b/fLcknSO/YxRZQ058CHtgiklkjcJeldSRedJQpJ5yS9LGnZwM7XBS+J/wCea5CK5pC73gAAAABJRU5E"
                    + "rkJggg=="},
            {"5",
                    "iVBORw0KGgoAAAANSUhEUgAAADAAAABGCAYAAACKeRdbAAAEbUlEQVR42u2by4scVRTGf/dWdY/xkWAMgtGFUdTJIksfOPiM4giC"
                    + "iBgNuHHhIsnWhRsXigv9A1QwqBtxJYIoKmYjolFBMYIGFIPvOMSMEiVOZvrxuehzZ27K9tFTt7qroC803VXQVd933ufcKiQhyUvK"
                    + "7fe1kt6Q9KekviazTko6IOlmw9Q2jBQ/AXzLTtwj6ZDqsw5L2m3YWpLcMAIB/H2SFu2P/RqADxhOSLo/0oQrEkDSnKQf7A/dGmkg"
                    + "YFmUdJeBb8cEPNACHgAuAnpARn1WZpg2A3uBM+28J/pxAXAlIMBRv+UM2xXANqBTJLDNGLoaE3DAWcDlEUYXCGysKfBh5nROUdDe"
                    + "bEwNINAHfrPv1ZUXT9RwBeEeAw5HPqGgAdcAAg54D/jWomY/1oBqakIyoBnwOfBMhFUxgRQ3qUKL3sB/CewDPjHpd1IScBUmvi7w"
                    + "JvAEcNDu0y1aS17SLj8AvgbaicxQUbT5CHgH+NFwDo2WZQkcBB4CzjDppEhY/ehauZlN958EVNaE2marKQJBEIoHZuxc79/Ap3Ji"
                    + "RYkmRdnQH0UgeUKn0ySu4Wn4yhOp3VUgDP0fraQgEKJGWR/IotLGRU7cq9qJNwMbSla1uYFeBpZMICt2rm3HvZQEQui8A7g+ysYq"
                    + "aYpd4ATwlSWyA5bIMsPaTakBB1xakW/eBOwGPgXeAl4Cvo+S2qqwUuaB1G3kRuAG++wEHrWSum0FnQOUl+yFq+yjY+HcYlOTBwsk"
                    + "ap0HQmgObe8s8CwwZw7eip2xCQ19H9gOPAdcZ6TavmaDrP+KfD2bDz0OnEekItcgEn3gGmAX0PHAL1GSqLs5uaiMvxPY4q3TX2yI"
                    + "L8QkZoGdQQPvR/OWpqyzgfngGE8BH7M2DVYDNDAD7HCSAuirgf3AjjHPirTOQLIC/OSk1ZKiC1wFPALMJ+7WqlgdYCEQiElcaB4+"
                    + "B2wZgzmELHsxo+1R/I1AIBFmM62KS43QwJ8LvGgFW3+Ee3aAhXzINCwz8OOISr3ofsl64tDGVZ2ds6gVXa+glFc8Jql6DNP8scqU"
                    + "wJTAlMCUwJTAlEBdGpRktVDxwq7C0qJ0vZUPuZiPzovTN7OVGHyo/11ZAq7QDyzbcSirPWl35APoUJF2yxCIwXeA84HbrUfeVGFZ"
                    + "HQTTAbauw6Qc4PMC+MuAx4B7mcy0btR7ujwCvx14nsHYLtj9OIGPCr4PnArPIGwFnjbwPdZmpnVeXWAhjNf3AjdG4Os87A2RcAU4"
                    + "4s1pb00R0sa8TgIfeuASBs+ONgV80MAXwOve5jJNkXpsPq8BR72poilT6TD4OgS8Asx44DvgD+r78F8MPgOOA0/atzxwFPiMeu8P"
                    + "hOh4DNgDvGpkTnmzp/3Az6ztD9TF3sPULmOwU78HeNmOlwAhKbNn8XdJOl6jFyDi9a6keXt/YCZ+CcJJclE5cTeDLczZCUt/Cfgd"
                    + "OMLg0csXgAWrjpdjUw/j9ZjEbcDDVo1umICj/mo++baZyzcMdiVD+DzNT/8CY1Cki7OIsYgAAAAASUVORK5CYII="},
            {"6",
                    "iVBORw0KGgoAAAANSUhEUgAAADAAAABGCAYAAACKeRdbAAAFSUlEQVR42u2bXWgcVRTHf3N3ko2NtqY2flAERQUFKbYIKhFBtPUD"
                    + "qoU+KBW1KqKI+FDEN0Gh+AU+SMW+6JMaxAcFCyoIivoQRan6YqW2agWpSlo0hmiSnf37sOe21+mu3b0zm92FHLhMNszsnP/5uuec"
                    + "exZJlLCGJDlJayQ9LemwpExS3daMpLckrbf7hyUlZby7LOYrxvwLkubUnOqSpiRNlAmiKPOpMT8uabcxqeCaByBJ+wMQXnNJLwBU"
                    + "jIERSTsDc6mrNdXsulfSOmO+EAhHHCVABVgEbgQesP/Jrq2oAmTAeuCe3P1JDCOxABxQB1YDdwFr7LNrE7yAK+35LJb5WABe+jXg"
                    + "DmBTG5Jv9h1jwLgBYCkBeObPA+4ERiMAeBoO/lYMgDRC+h70/cCGAswDLBQxnxgNDJvjbgLuNW3EOuBhW+lSAXDGaNVM52xz3Bjm"
                    + "a8C7wJ8mhHq3AXjH/QfYDFwfaTo+Uu0BXgGGDEy0D7gOHFfABcDDFjY7BeDvnwYmgaP2uRbLfLsA8mFzwl7Yqf94AO+Y+YTS76oP"
                    + "+B33IuDWQBudMu+AH4BXgbnApLoKwIfNKvCIpQCx0gd4HfjUvq+w9NsBMGyx+ibg9siI4wF/AbxRluTbAeBN5Qzg7iDfSSIk/xfw"
                    + "EvCtxf1CjtsOAG86C8A24LrIsBk67pum0dKY/z8AXvoXGoCYfMebzhHgbXPcpGjydrJcKAnC5rztuJd3kCo3o0ng/cBx3UnMrVnq"
                    + "3XKjS5toI7WwOWFxP41QuQe8F9ht0q8GuVM79YKa1ApqBSBfFVWBx2znjZF+Yv4zCRwAVrUZeeqmJe8naQ7ICQASSaHpOLt5G7AL"
                    + "OL1AtnkE2N+hv8wBB4GvgY8talWCTCBrBYAA7bhJ7tqCtl+EZk1zHwIvA/ssgmUngMj1d5C0Q9JsGx2GdiiLWPl3fiZpu6RR64Sk"
                    + "YVciDaSfAedbkT5akvRjn1ewrgDWmWW8aNHRdzdI7SWpMfwQcGlkvlMmJYHfZcApwBPG066gFJULdscbLO5X6C/yFdsK4HFgu/E7"
                    + "7FXsgBHgQeCsAmViN8n3oUaBR4GrzEqqzsrEm4NCpd+YD0F4P91hZl9xhuo222z6GYAHIeAaE/iCA64GLhsA5sMN9UxgC5A6YKNJ"
                    + "v9+Zz+dDG4AVzsrEEQaPxoCVKbDWOgSd5jwqqTBJ6LwxjO0N56aWeSZL8OJuOPTKNNIGE+Bn4FDB3mbNau1LYv2hCIDvgfusvxmj"
                    + "CV/1PWkAYnIvFZHekOUkf7QqNtps2RRKXYoAUK6GrkdowBUNBGlBRwrT3nrksz3J1/sqt+h1apAMKoAyNkH1WgPLPrAMYBnAMoAB"
                    + "p7TE70q6fH/pAHw3O4ncUcV/h0eWHMCirUoBRmQpdSEAilT9WuB5qwsUqcFFGudw0SaVFrD1i231yocAkpT4Q+dedSVCylJghkbP"
                    + "cWhAuhLe+TPgdwd8RaPBW1aKu1Q0DfziaJygzwwQ417rnwOzDpgCfhwQDXj+ZoGPgEVn0t9jIa3fQXj7/wb4BOtODwGvGSJHiaMw"
                    + "JZNvfE0Dz9EYWTuWCkwDO4GfCE4A+0zyzqzkWRrTLykwj02Nj9h1q6TfcpPmvaYs4OcZSacGE+/HDrqT4AcJWyX9Gsz8ZyUceHdK"
                    + "/r1eiPOSnpJ0Wv6wOzypTyRV7YYtdkJe7wMNHLLpgVXGeBr+1iCclfAx1s/1nENj3Gaj5TxjNA4Vuk01Gh3vo8CXNEbVpjg+9pyF"
                    + "0TIPwJM/csoMyGbgFhrHUavpzmG4gL+B74APgPcMwDzHO9lZPtT/C0smWU4Nrxn+AAAAAElFTkSuQmCC"},
    };
}
