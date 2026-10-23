package com.example.data

enum class AppScreen(val title: String) {
    HOME("Home"),
    EXPLORE("Explore"),
    LIBRARY("Library"),
    UPGRADE_STREAMING("Kvalita streamovania"),
    NOW_PLAYING("Now Playing"),
    ARTIST_DETAIL("The Midnight"),
    PLAYLIST_DETAIL("Synthwave Nights"),
    EQUALIZER("Ekvalizér A Zvukové Profily"),
    SERVER_LOGIN("Server Login")
}

data class TrackItem(
    val id: String,
    val title: String,
    val artist: String,
    val album: String,
    val duration: String,
    val badge: String,
    val imageUrl: String,
    val playCount: String? = null,
    val isDownloaded: Boolean = false,
    val isLiked: Boolean = false,
    val isHiResBadgeCyan: Boolean = true
)

data class AlbumItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val techSpec: String,
    val badge: String,
    val imageUrl: String,
    val isDownloaded: Boolean = false,
    val isOutlineBadge: Boolean = false
)

data class ArtistItem(
    val id: String,
    val name: String,
    val trackCount: String,
    val imageUrl: String
)

data class PlaylistItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val trackCount: String,
    val durationOrExtra: String = "",
    val badge: String = "",
    val imageUrl: String,
    val collageUrls: List<String> = emptyList(),
    val isPinned: Boolean = false,
    val isOffline: Boolean = false,
    val isStarred: Boolean = false,
    val isSynced: Boolean = false
)

data class EqBandInfo(
    val index: Int,
    val freqLabel: String,
    val subLabel: String,
    val fullFreq: String
)

data class HeadphoneProfile(
    val id: String,
    val name: String,
    val targetInfo: String,
    val preampDb: String,
    val filtersSpec: String,
    val impedanceSpec: String
)

object SoundWaveCatalog {
    // Hotlinked URLs from the HTML templates
    const val LOGO_URL = "https://lh3.googleusercontent.com/aida/AEtjO1XNbJU8Bha_PoiUwOZeH1JHNTJyMGvtEGBT8Zk0fwYou9udUULl9u7BSZHEm48k2Hr9r9pBhEQlnq4BHbaXSGx_s2eNT8brkl6gBiFr9XsnXv2sFLYDwIT65KI06sl-DuIHczyPGpQtLNvOKnUHlQYFcdUoH6WgKeaDFqfUNPHQgkCG71_fcQwxHqi1GtwWTNP20eBS_QPJIlUSowtqpNuRGviC_3mzrOBC6UodihHvP0xTC9ZrCKcE80A"
    const val PROFILE_AVATAR_URL = "https://lh3.googleusercontent.com/aida-public/AB6AXuDJbRXDLCgliMAa42OjzEx7JNhbXFpEOifUxVb7uKaXETuWAT6cOCjIG4ctvJTGrHglfS4kCJiqvn_R8aeEVO91I-IRPnKCKyZk0VtKEWojOFiB2sw4ykQmWoHfukCeSmfDX__S9KHac9lFsTBUW1djAMY90G9sya6LgS3USaKoHUB7UQhagpY1hUAnS2fPbaA4tyRv6WtJGa9nZOWq-y6p-0tmIGzdeHezp8-6pPG9_1pbI0uII6RI"

    // Home Quick Picks
    val homeQuickPicks = listOf(
        TrackItem(
            id = "qp_starboy",
            title = "Starboy",
            artist = "The Weeknd, Daft Punk",
            album = "Starboy",
            duration = "3:50",
            badge = "FLAC",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDAZnbeYi6rC9pRmdEWffS0Zy45kO2Mb86kiFLd0tV_vujKZGECjGxutequVUqkZw3qwel0XpolUuYVsJZsq-rCw_5JlQrDOzJ7vOwvxihJkxho6Br0hr6pHiag0jCIvhrlp18Qn6Dfb0r2k0axyfZNOt5i8n6Fq1fGX231CnScFwqVjC8c1mgMitOoPbOiKiQ6epULeMVtIP4ZNy_FJ_udtd_LfwBSHBI1D1Ocs5LKtflV_pm8GIz7"
        ),
        TrackItem(
            id = "qp_midnight_city",
            title = "Midnight City",
            artist = "M83",
            album = "Hurry Up, We're Dreaming",
            duration = "4:03",
            badge = "24-BIT",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCVy_POb48tA01_SSUyj_0YXnRcxLXXjzUBvEg6Go-HngStL2wdwFjt1zmqOgGN_1WzOQ_TXQ9NTpGU50GgbkR6F4U7GeBROpm-j38ih1XOPk75uN4M2zKoRSXlhShRUfzb6gUqEN03UpmRkulqbQy591JKkKdCezHWZIlZoE2OOlekc_n302H9Rm3OpcV-2rH0i7--E7Ko_qpa-6sOGGmQFNARcI6B34Xk2kv9ORK_Ynv26SD78LUe"
        ),
        TrackItem(
            id = "qp_blinding_lights",
            title = "Blinding Lights",
            artist = "The Weeknd",
            album = "After Hours",
            duration = "3:20",
            badge = "FLAC",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDgctnuEQS1S6Tyui2O2vOwqT_ZIdt5wNPBXabgVEoQ--zMRPqlis6_yVHjYDOvsojJrbOXIxiKzAnyv82EswvNPY19TIy4iCbzKVdvaWXgIIPexqIK3Z-O8SGGEzlKR2ENnJhaWcFUn_BImbjYrFSyHYzrWz9haLze51Sx5vvIJMDuWIPkYlK1yreGJT4Wziszf1hAeIwSapXHgK0ckgAKhl2I72IXT3oPxI3Qp_fUA6cmPwNr5xGZ"
        ),
        TrackItem(
            id = "qp_after_dark",
            title = "After Dark",
            artist = "Mr.Kitty",
            album = "Time",
            duration = "4:18",
            badge = "FLAC 24-BIT",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDtgxCfqteJwum0NNgzHKL4codlmYYoS94SHo46HWecz_qw1agcgcmHAKCVFNX0JhEJCGcBLJf3BIIRWrFtn3i_Jz5pVw7tBYXUm9e5g5GAf_SQVnF7uNig1W7jA8jYn-VBNAEIhH456ziXZCPq78Q8YlCaMhTDXH6YB07xtKj18P1Urbe--Q-XbvmO459Ck9HLK3v00z9hbd31gGCQdqgAb7z4vLmWGXe63TWtNA816M4N0YUXLmuc",
            isLiked = true
        )
    )

    // Home Listen Again
    val homeListenAgain = listOf(
        AlbumItem(
            id = "la_dawn_fm",
            title = "Dawn FM",
            subtitle = "The Weeknd • Album",
            techSpec = "FLAC 24-bit",
            badge = "FLAC",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuATy8UlojYMCLc6SJbsZaCYHfjoZy4VVYY8tDITPTVt_qo3TzXSr-iROgSqxQ5UURFPae_dnZPa-Mb00Rg99CbHo30kzyKNAApdP7aRUYQspSWeAqCG2oSFh447nPNHAAsMrBeLb3FqoIHT7t5kpApc2-77Mu4Ep_p5FiZfS4wesm1LvMTzFrn8xFEw_F03ZTFCB-Wn5puWDHpkY7IW5lP5GCAj72LMb0Yzr6sKibPKBmUWLCn0OXit"
        ),
        AlbumItem(
            id = "la_currents",
            title = "Currents",
            subtitle = "Tame Impala • Album",
            techSpec = "FLAC 24-bit",
            badge = "24-BIT",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAvRrYA3-OnSJGJCzTx27-XqylMpdRX829Qu-z58HDC3R8fw2bEaC5ZxNbGCeYLMH6uqM70mzK6iZLhMCb_n_ui2YzNNsMj_tfi3ihp56ePzVW-Biy-icmqxcB8ojbtWqRD8DGkKBddjWVNI7edXm_s6vu32Ea29uFCJhIeoXjr63N8Pm-LP5X0axo553wa8ohr8dpmWa9s4wNIT2yRaxY8UuHWxgtf-6BuvZSIMhiddaW43Kxmy_hE"
        ),
        AlbumItem(
            id = "la_ram",
            title = "RAM",
            subtitle = "Daft Punk • Album",
            techSpec = "24-bit/88.2kHz",
            badge = "HI-RES",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBV5Tisoffc1cq-qW186JndWB-97KYJ0jd87hko5M57TNQ-jEIsCTon1JbFG3i4xY-9pxF7HyRzcvxJLDvUVnyNCg2FdULWNLpU71s4T_jpl--LlX21KsfaevqXKO_khmPHU8iiDVK9hWTGLlsho0G5PLv1WVIcApBCk1epBIVXw8g0yRP-IZ4fxgmxOU7V-Q-TJxwWA0PycRpKuuqFfTAiCYy6KnOWAgsIOmSLgZlp3vOi0eZrtTOt"
        ),
        AlbumItem(
            id = "la_future_nostalgia",
            title = "Future Nostalgia",
            subtitle = "Dua Lipa • Album",
            techSpec = "FLAC 16-bit",
            badge = "FLAC",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDIoIht242NfwHI5pxTE2KtfZboRbPvlsb4Avebg1A0ojKNMIBTyIA1TUHgwBELWyQLEcXZ2OVSonlnlqKRq909mhtGliF5jZRYcg5BJ-UqVZaBeu0Ty9rzaYCXCQzN2DuccX6efaj1MM2WE-3Adp5REYCY6JQ-5_nl8oRYOcfgDEq051R_MfWPFzR7sD-12hdup0fUx6u2E7qtU2xAHWH7w21VdeqJ4wradde1wrmT_dAoqujQOEyz"
        )
    )

    // Home Mixed For You
    val homeMixedForYou = listOf(
        PlaylistItem(
            id = "mix_supermix",
            title = "My Supermix",
            subtitle = "Updated daily",
            trackCount = "Mr.Kitty, The Weeknd, Kavinsky, and M83",
            badge = "graphic_eq",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDIc0IpdtkS3K1aZP1bbAqT10BQ9JdHgZ7BES1cqa_b_NZvoZKF3nY5tHFIdf8WWgVIIPDhBKU7z8RcEk97oC-K459du6oVthFljAFAjRTmMkSEy6CVbSODOlcg7yFgzQ-o17HkUz0rN27De0HiXmKyPZlMvcv-oRxQ6azrV5Is6Ax1pyV3F9fhASFybm7f4YsP7VF-ugLJC4gzgXFs_Y1C2yhy9mHuBHY_KuRbm2N2aFXiayZyR3DA",
            collageUrls = listOf(
                "https://lh3.googleusercontent.com/aida-public/AB6AXuDIc0IpdtkS3K1aZP1bbAqT10BQ9JdHgZ7BES1cqa_b_NZvoZKF3nY5tHFIdf8WWgVIIPDhBKU7z8RcEk97oC-K459du6oVthFljAFAjRTmMkSEy6CVbSODOlcg7yFgzQ-o17HkUz0rN27De0HiXmKyPZlMvcv-oRxQ6azrV5Is6Ax1pyV3F9fhASFybm7f4YsP7VF-ugLJC4gzgXFs_Y1C2yhy9mHuBHY_KuRbm2N2aFXiayZyR3DA",
                "https://lh3.googleusercontent.com/aida-public/AB6AXuCfimtYeBFcZ5gXnp-iSAlBvE7CGbwWb7Wejml-ovV00yfCLcKQwEoSAk74psc77upPfD9z10NIGkddJ7sBHQWFGFBu-jt8TyX5jFotGc0baFZAdxeSFvvySo_I89OkFf9TN6PCPO7PFEdA6eD8iuiKYtZ5OewScr5SSV_xFOmCeVECfp0BV-fZmiAd-GPMVbt2LzWLVLT7Grr_2dUXlktxLTeSk7aFOIgxc_Vk3UFIq2P9CKslmXHH",
                "https://lh3.googleusercontent.com/aida-public/AB6AXuDsrgB9egI12dljRSsh1mLtkzbHreSIBoE6rUlxdUstGHt1_9F8XMZCEsJ8UqtPoraiAfvuQ4HnpB2VJOkshzhUJDWi_5XlupA19SxD7csKZpjvCt8941LVedtLGH1VFll-bGqFNBkwCEfGzDHek9bv_LQFpBUoMGmNjnIvTOvavfDAgkwHjA2yRNxH0DXundTV_gprppg2nRsPBEe9e0j2My_rRDiwpGyfNiYGKZqLVhS69fPhFsp5",
                "https://lh3.googleusercontent.com/aida-public/AB6AXuD-jC66Ga6GNAx7QwidjVwmZ0RCIQGChuvN_SJfqxEASnR1EYc0QSocegHtSmPOXXDl7TawAwk9GleNPDKlTsbPjI4NWTgDQz5hZoMMg6I51elFftF7JGN2Tsx14DUumojhRL7XZ2kCCEe-hNn6m1X9p1es2Cwx9Y7j0gxJiQdzmxuVhUokkr6jqTJ-fIF53Z8_BQCfcGpQk3Qlvh9PIFvm27GjWFI9mzEmJVtQ3ALhZeB6KtNz77_h"
            )
        ),
        PlaylistItem(
            id = "mix_night_drive",
            title = "Night Drive Mix",
            subtitle = "Heavy Synths",
            trackCount = "Gunship, Carpenter Brut, Perturbator, and more",
            badge = "radio",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDXQfHlN1mllvxdM9Jv99eLhjh9pT90jg4YYul1VAhO9tHMiZ_xQLfWuB4lWM8VWKJ4rDfueEseV6Bpez4HEELjzcKupXwNZwih2ptKt86Nx56flTjROOPZe2mNETYuz4VMZ6hEyHROGDfwJnvIu3B-09PmIcTX-_jScD4-dmLt4sFeZulh0PVT1_tVYnXks6IhML1KvjKlUAAg0WDYGe2ITZgc01dxVD0zr9wEZ1Gj1BLoaKS8fvHj"
        ),
        PlaylistItem(
            id = "mix_deep_focus",
            title = "Deep Focus Mix",
            subtitle = "Ambient & Lo-Fi",
            trackCount = "Brian Eno, Tycho, Jon Hopkins, and Nils Frahm",
            badge = "headset",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCKZARbk4fzdoohl8PhwAl64g31ON8cyEe694gNF_mRVNNP58vqlvd9tpSonqT_txgNFtjRsI8yuN-BLT0pPnAcDIVp-Dlxm73UcvgbluNCwD3Rm458wCVYi8Fse2aVsdsMNIiGjE_yZY9T-p2XO9FhClDddEN7RfDG2PNkDUR4j0zVenZX-ubWuWLu2a0eMckCfGejcnf_GiLoxYcmSdgbiKORev42RZnl4i0WHgoLQ_IPixCXI8-I"
        )
    )

    // Explore Screen Data (Image_1 / Image_2)
    const val EXPLORE_TOP_MATCH_IMAGE = "https://lh3.googleusercontent.com/aida-public/AB6AXuAtvNzKlGvTHJtzkVhJXrmGm-rPToqkq77tgpGxaOf7RI1BRtDafZ-__knscVPjdbF4ADSD2fcuw008dMz71fbyGTgjDTF0x5RAZdokNCy3O94leVTkpcUiPWl64KUI0NM6ftLzv7cBJvFISgBDEkEgLPdBLiUpGkC1l4jE_6Ci1Bk1IFmQpzS5zrQgYPtAnV8O4uDUPlPGMlVUGKr1R7qZOYH7DGlRduuCL-Gv-japzbinTZXr3H53"

    val exploreTracks = listOf(
        TrackItem(
            id = "exp_days_of_thunder",
            title = "Days of Thunder",
            artist = "The Midnight",
            album = "Days of Thunder (EP)",
            duration = "4:38",
            badge = "24-BIT",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAREQ4X5Vy8FCo1wmfdB70SZdj6ueZ5uD5b8AO6UM68hqiMyovBx4zbiWC28KGZk-952BMODXpZRquIncIdi9L2cUwz7oZboQkdc-w6yqrdIC2s5KYoEWhCbcpfGurVRPciyvF_KODZzA3JDi8tIBsQiI1vDdu0zadTewl0hyuznmt-UzWS94jGiUBPZ3DL5xKHhnVpSkrBz0owKnuGHieHrdi8NVNVO5N5JV_n4g70fz7kGZVvb90P",
            isLiked = true
        ),
        TrackItem(
            id = "exp_nightcall",
            title = "Nightcall",
            artist = "Kavinsky",
            album = "OutRun",
            duration = "4:19",
            badge = "FLAC",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAi_atiTApjNswAAHjotQPSSemPNV2MbxqpODB1dc8KRvOgex-AfW5orBENY8-hgRvxGQ41BhnOUtWglyuhW--9WXi9bOgUnSqqptUK1S0vc9vs4GXldwrFzigFyarEP9OrOpNxvvlWiHslSXLjlzx0JITZ-Jd4nSaz83_obioKLN6C0-gFZClTHqqSIbZtEMZm0KTAP0G3eFphhUKxJ4ra1kQeBcxmy5K08JNaEAVUaHgTUzN26Ih5"
        ),
        TrackItem(
            id = "exp_resonance",
            title = "Resonance",
            artist = "HOME",
            album = "Odyssey",
            duration = "3:32",
            badge = "96kHz",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCxbX5G9ztEOnAZz9ww6Z6ltyR9SzNaQbDF7_pdzzIBUrPB9QFXw1Fv_8ygqhpy9_f0N99nRPmEaods8lllGP7wOvnShU20Nt-cDrp9UltvmZGlOK6DYwPpJMy_PQyA7cFwG7GyjBe0MOqyUgOV-ahakizorLwzhxxbdTINfYBgIqzqId1zbFPA16qDfnek7PdDuoDoyF8XLtpqbYbsAh9A5JvDYeGfZMPDEEQIOk552nnN9klSyRgL",
            isHiResBadgeCyan = false
        ),
        TrackItem(
            id = "exp_tech_noir",
            title = "Tech Noir",
            artist = "GUNSHIP",
            album = "GUNSHIP (Deluxe)",
            duration = "4:57",
            badge = "24-BIT",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAOQAftXweJTxp8uelTYz963wfOdx1-mD2Hxn0vHXohItVCOBRfxTld15n5HBRODI3eTJ5n11cqMGCnkrKwY270vGY6xiReGH-OqzhiyztzdK1aJvinQxxaVc4jMnzh_s9-D5mZvy8d8E9uzQvGN5Qv1D2etm8pzkuzVouFvdIJU_buHfjfBL9y9XgpLnNqsEvICqnX99SMYj2hNs0J5j-7wMYA6Muo3MNzaf4q3JMV_TTZdFWhj3Cn"
        )
    )

    val exploreAlbums = listOf(
        AlbumItem(
            id = "exp_alb_endless_summer",
            title = "Endless Summer",
            subtitle = "The Midnight · 2016",
            techSpec = "12 skladieb · 24-bit/48kHz",
            badge = "FLAC",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAA_Z34I3VLNm36Wz9DN075nQWQvwRtZkaiG3hDzUhPL8Ere5j9xYEozbxeM23kokaCPGmd1odOAYEGsfKm9nfsJtaMqFF9B1aSTWOnbUCAL2LVGtYSBTjWOL4suv4S8AWQT7u_Cr8VyY2zOkzSJRlZmCAPhwHh_xMtgEJrnhH8PCSd8ZcmzggSVzLiivbTAsFLRTv1mFpNjK9OGQ20d4aaj5TJoOUnt-nC7JWc-K4vQdDNMACj6KZW"
        ),
        AlbumItem(
            id = "exp_alb_monsters",
            title = "Monsters",
            subtitle = "The Midnight · 2020",
            techSpec = "15 skladieb · 24-bit/96kHz",
            badge = "24-BIT",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBNgvJK9AJJ19uGYGEh3Lg_SA0263ahhFEgyhOb-X8GLcgEjxxAfCaJvTWBGfjmFWGyEaYFCmkulP7SfQ6waqvOItwrNApJWS1bbshEmDP7FKukl2YEgY6FTFr4fpta7PFDEJ5Q1Vmq_g-P9WSwEXWsFGOu_wxAd8CJ_RyxOG5h57QD2Rr7S_uCJ0g4ovNKmPeiaU7q--7pF-Y7J7i7aN1k9ZdXm056z37MnN7jxA107qbjanTyNwW_"
        )
    )

    val exploreArtists = listOf(
        ArtistItem(
            id = "art_kavinsky",
            name = "Kavinsky",
            trackCount = "42 skladieb",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBLaCqS7ya8XuDVJxkgl5jHimOGSAKodTZMtDYmSKJLapCUwntQdEp2vXStwzRwz-jxgYEPPqjlGUCDQqMCv7cOkpQgteWTiX_zq8i7Ul7fJ5yRrokId4Dg-ho8mpIwIQ05JdktVKGfDNSU23W6VduamygNRa5wp7TP8TIYDhj6IONDjED9k7k4c5Ozo5KUxnfGYDkUzVb3PkKh1Ffi5BwPQu64DXvz2ocawkZYBObR0eC-dRoUheEl"
        ),
        ArtistItem(
            id = "art_gunship",
            name = "GUNSHIP",
            trackCount = "38 skladieb",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAnL0R9adgmGgE2SgLF9-vRNeEAnmAfnUPY8r4mUsTmsIG1wlBf762kKHzJ8oGdWnt2tOJU3FlAaFWPPPCyppli6OzWBNSkwU7uM0zlDWgl1uYTyqRpPc1y0LKSgDpcOhhfmcyqnj3uadTFyBLkw_DTbTTmYmoN40tyhbRRNK7uwRAVRHpvsf8UAd-vgEcqjllhxHrDfKchY8m1kDtGOuyASPZULEFouQCLTe1HpK_Nr7gOJCdRcNu8"
        ),
        ArtistItem(
            id = "art_timecop1983",
            name = "Timecop1983",
            trackCount = "56 skladieb",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBA4Snyx1l9UcVGfWNHQIUh4ELOkFEhtcycWRKQv5E3_3UvNNeVsT-kFIjK1CX255eKC8DI7-gm8TD2GGgqGJ7wvWyM7hcIMCWdLAZ5Lz6pUAUh2kde3qD_HuZGTNk385IcFRRwnlqmfqTO-MQwnICAACJE-M1fX-6zTS9y_1c9UfrriymOnFSBmHJdK5uHaufsOeTZgs7XkwxElHGlxyaSIyX7vpOehJwuItSwTJQsJViAg-l4vWJe"
        )
    )

    // Artist Detail: The Midnight (Image_3 / Image_4)
    const val THE_MIDNIGHT_HERO_URL = "https://lh3.googleusercontent.com/aida-public/AB6AXuBMzGxGpXoPP02nkK_8IsJRdFq1NwhuPR2tQQR0EIobddN2b7bl3lF0KnFlhv4RXc5BHTYYbgE0a6UU_jgmRkl14XPp2GIkU7TIHTR3J44MW6gOBanU-AgNw1LUVfsXu75ky2fcc56xOD9M46EDE2d4KC0DJ19c09NHZO16mf06dPGKo7i-XzCmI3ZIgerg4HikIuCnC0D7XowafK2wmn3-H5YFJ_neNagEvUI9tGkKjGNenCe0hIvL"

    val artistTopTracks = listOf(
        TrackItem(
            id = "tm_sunset",
            title = "Sunset",
            artist = "The Midnight",
            album = "Endless Summer",
            duration = "5:26",
            badge = "FLAC 24b/96k",
            playCount = "452×",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDppKcxbluFi2kPV9LNPzGy0B0Mcx2bB9_29hKiVrNtC-ZO6ojTzhIpaIY1MoiukXSSSVwCiBKq9vofdzWZBiILdPQuQYSOQliRFBmLjYzPrSVpY297X9SNhddwXRc6FWF4taKRJrgs0-GTNiByJ_OVf2GHB5kXAwlvWNRFKlW9rVjO4bn2dUDA5dIXplbwCD56ats3qe4U3znKVfgyDcCl4WnJX1uqdJtx1Kyil7AcLAPrM25Echib"
        ),
        TrackItem(
            id = "tm_days_of_thunder",
            title = "Days of Thunder",
            artist = "The Midnight",
            album = "Days of Thunder",
            duration = "5:19",
            badge = "FLAC 24b",
            playCount = "389×",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuD2YPuL9_kV2h9cIS94rTpcplQHBc6Rg2XstBI1rvqrFdUXHH6gbg6UNnR_bAunNmCTGFhd-677APeST0et2rrr3sAAWh_KjqKQ4K0A4ZwOAHbFDstd2vIMa54GBGJc751Km_4_mVA4EKU2CCJVhAf7qzWqwoYHePAFIs5GUkxt7FgOgPKkg8cOJEpF2H6EOvAAqPZbnd403PZBOBxW-lxg0MQ5g3BWetdeTeCcjXcxRjpOBg1Crm2g"
        ),
        TrackItem(
            id = "tm_los_angeles",
            title = "Los Angeles",
            artist = "The Midnight",
            album = "Days of Thunder",
            duration = "6:30",
            badge = "FLAC 16b",
            playCount = "312×",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDE2tBLQJunHOvGk1b4uYHejzsggLouAN8U39qsH0cE13i70Kvy88HiomNeL9zBpHa-ANCn7xpRVZ0iI181EN2-IlFnpJjLuog9i5_9Gb5YFDcYSBO8wtQFccPFtuNAG2vyQof1Z0-jT5z1RFFCKTtLPx5m_vF7JAZ216ZqFjY1Twm-rxX_F9GdpjoNRWh_Q0DXuczffhbm74AsvugqxnPM26zEoV6050S8uHl9MRshwZwXS3eJ1rDo",
            isHiResBadgeCyan = false
        ),
        TrackItem(
            id = "tm_jason",
            title = "Jason (feat. Nikki Flores)",
            artist = "The Midnight",
            album = "Endless Summer",
            duration = "5:31",
            badge = "FLAC 16b",
            playCount = "298×",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBTStcxccFiFwyVkyJtGE2tSzMgEsbsxFstnaIptzsbhNTjwhNMrnth8p_TlLO_2KmDt9NjIQB2QOeEBPja48hGZqA5xrpvCjw9aS6e4xioMx4Jsqk7_8Np0O_4pkUpf-l2q7xVi1sSG2qbNzn3EEVy2MKGegxEwWPyuiNLNoBBeR_0VEHEEhMuGTFYCiOk5JKOqP3JU02bK54Ld81bnY_zPBUEedufxvi7TYxdprba4VopzoeUQrRd",
            isHiResBadgeCyan = false
        ),
        TrackItem(
            id = "tm_vampires",
            title = "Vampires",
            artist = "The Midnight",
            album = "Endless Summer",
            duration = "5:17",
            badge = "FLAC 24b/48k",
            playCount = "264×",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCFJ2SfPGpNCWySuGMavs7_Q9rrwKL2Qe3gRa9GquqSy9bJF5nsjr2gOU5yByj3toCDX7eML89XTF4fLtKGaWTlP07YLy7EHOU6A0hOaJ-pcCyg1dZBWCOO-yd4N_LI3_SJgqVppZQurlbJSqUzsbJg7Yx05IKRHGylODiFdXdMpROA3CrXXnexS230WbH9e8xoWITN5LrxO4MiqnqQfyP_k6ceDINW6VTwnJ4WOsqIsdXRM1e8arQG"
        )
    )

    val artistStudioAlbums = listOf(
        AlbumItem(
            id = "tm_alb_endless_summer_deluxe",
            title = "Endless Summer (Deluxe)",
            subtitle = "2016 • 16 skladieb",
            techSpec = "Offline pripravené (1.1 GB)",
            badge = "FLAC 24/48",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDfLlbCaNqddqieUB4dwppjkbw1hqUDGixH72kDxCPXULliIExi_CQvPca60q1yr3iaZ1mBSmYlhQlaOdPPCOLPTUc-hXAOjk0Y6FqAchIdSrVrDxpK7AXnKJ-fYD2LOqJzLF0jK82FWb5LBQ2Ee31c8mUkBhZkZ7daPuGKra7dnFtlokJuJ4B4daoznxGZnoU1l_9kQLcDJmQ8AI8tdrffxk64iqK5lK6qH3ZIQXcnjL9sny7INlSJ",
            isDownloaded = true
        ),
        AlbumItem(
            id = "tm_alb_monsters",
            title = "Monsters",
            subtitle = "2020 • 15 skladieb",
            techSpec = "Stream z domáceho NAS",
            badge = "FLAC 24/96",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCBW8NBNjWsm0y-C6SBFfnBRXPxyY3RkQ1SctM7wv0bElDzXnw2NM0rDNow5fg16zWS_8sSho9HTB6o8FHkQRZF1zodpn0sL6okL_es0gX3embq9CKphDJmq3HE69QjR2WmTEbiRJz8SuzaoM6yjurBtNfv7_EE6SuskMNUyBd3cNYDigKBOsp7hKM5QELmflm0yC_8LvCFMt0OHkWv2g4NOcJEAwJtgXTiFfS-wH4fiLwM5ZKtQCgZ"
        ),
        AlbumItem(
            id = "tm_alb_heroes",
            title = "Heroes",
            subtitle = "2022 • 13 skladieb",
            techSpec = "Stream z domáceho NAS",
            badge = "FLAC 24-BIT",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDylz0fUt3hVnbPhfJ3On5m9jFvePPbIGmQj_OAYlEIGBt7RGxPUZajEHCv4bsFtU42VNu6qldU6PFcR3qlIuUjJo6Ji_VAvFQi8SNL799dUu-ckUUlD2UnpLng_KjiXXYg8F3PT4oQImtjjRwDkJC0zc10utY9f3f-RbRYln3fCEheL7M42ROtvOuX5SNjyaBoGiFu9Ge8BS_brgTfP5O7Np_yRCj3h_UxFs61F10SdR4o8Wpgi9Sr"
        ),
        AlbumItem(
            id = "tm_alb_kids",
            title = "Kids",
            subtitle = "2018 • 9 skladieb",
            techSpec = "Stream z domáceho NAS",
            badge = "FLAC 16-BIT",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuD0JVtxcGDNX2rZeg_C1PBY1DdXJ4k4tXn-OqH5G9Ly5tYjkdG4bluq2VdmnuRAdHlh83mLYinPNfYVjUnKag-irgmnaiGY6Hm7CknQidfoevt6xQLUTVaR44l-VlDbyikTJ5idwTgRoxbluXI9dOQsJVm8-OXUkaZQ2bxhPJl6gDmJfUqGwOY2236k0QgRqOoZZ55h6II6gS5fwNIURo4PsUgdYhuhNjwFCfIg6OrsIzSac78k2tdA",
            isOutlineBadge = true
        )
    )

    val artistNocturnalAlbum = AlbumItem(
        id = "tm_alb_nocturnal",
        title = "Nocturnal",
        subtitle = "2017 • 7 skladieb • Synthwave Noir",
        techSpec = "Lossless Audiofile Master",
        badge = "24/44.1",
        imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBZpY2gAAvR-FGmrGclRs_7EVPkFLWsfnA1Ns1EvGgAOZd_mrnagPBm96US9Kn_48m0xKVgQjC4nrU_c_MQ4K7ReofCvllMGbtgAXcWaQBgnjVN_nMft9F1Ehzv2qKk9tc7LQyh7tgk_ifK9NZddsO7U6d_1anh-PqaQLUhmRGbLmUSlgxFkFXXxRU7-Cx5xhi1kZTgF_1eezd3kP6hgvUf3ouKhwKBXKYJTSlktslQiKLbA7tlOmLv"
    )

    val artistSinglesAndEps = listOf(
        AlbumItem(
            id = "tm_ep_chariot",
            title = "Chariot",
            subtitle = "Single 2024",
            techSpec = "",
            badge = "FLAC 24b",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBIhZ0uglQFQoFqJXnpC-WXHgaAH3C6h5Lj5Eldqlbm4U9OMK1Ju9k8fKjdXWRRGoVy6kWw_YhOJTrffnXvx3EA0fGvQ1y-dk9oGpRLBI9m2GaOIVMKIACZuS7sOJyXXGGTt4JCxKN8I-nMwgfXfYW5cUeij9Ox6tOCXeHgVMld8W8VsIgT7qYEKh-RVQYim-JWlNmffaJ_nor7ZErsQ7xur7eMpV7BOYzJK8dlgpGyN09-K4u7Og7g"
        ),
        AlbumItem(
            id = "tm_ep_dot_inst",
            title = "Days of Thunder (Inst.)",
            subtitle = "EP 2014 • 6 tracks",
            techSpec = "",
            badge = "Instrumental",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBKnOYxQFYp_vW_Dh9XHChCvJ43-PSYPoe3mSjA__k7bA5waZVu09UIv3o1Oo410_cQWjyLWqytAogkSWGcSsyeVsYlMGhDMKzr1aoqHnLZ8z5HeerfoStkO9sCK5mV4dl_edAB4Xz5cuDBouCDvlcrLPP9ZrrlNBIINCwj67N_l8VIzrregM_8L5q8yacinNEYcHsEsELjbG-7-SlKrxlfNn3ivCRT7K9bxo70S2IYETDpEn6oWWla",
            isOutlineBadge = true
        ),
        AlbumItem(
            id = "tm_ep_rearview",
            title = "The Rearview Mirror",
            subtitle = "Remix EP 2021",
            techSpec = "",
            badge = "Remix EP",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBGUyhrbOKA20vZYO06ZJbpTV9CFuJ8W3T5Bd0QW3slJhbTQuQX_9NYnYSOXlmgHsDWnxNg2HtcFrlNdAo3No4ZoSr9Qd4wnZgFpQauUt68PQskk_r_Fn5s3crdX_5SjWV-vgKux8C1VcZNitWukvP0Xf4m1L_Oj_iboOmYxHmYpREa4fn60PXhouQMI3dHcgXh5JoNG9pFa5WM_vBZflguiJZZa0cCmJptT8t68KyokRqaToPfAdtl"
        ),
        AlbumItem(
            id = "tm_ep_roundhouse",
            title = "Live at Roundhouse",
            subtitle = "Live 2023",
            techSpec = "",
            badge = "Live Session",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDizmnvCGyjBbMyN6WhNQsoi-LHfnrSwuxnEGG4zS3r8bR2GepHj3bzul8GRKfgJdujPr9-RVW8LluewCZahBuH90XRwAZ1630Pb7PvWYw0_scXAEitBsTHK6j8Keqi4iGkD0O84zjlQ1z-Z8GvrKB24BLrRPpe-Xs5_FtpC1OzFpiEYSgWl-ocpWBEdjvKdTjDz65tqNMho4dxTV9rakZQjCUQc0Bf-kZ8vAvF42vkDzkeRl-cQxvz"
        )
    )

    // Playlist Detail: Synthwave Nights (Image_9 / Image_10)
    const val SYNTHWAVE_NIGHTS_COVER_URL = "https://lh3.googleusercontent.com/aida-public/AB6AXuCZcGFrsdEq9piNuZ7N19g4noqeeO9zT_1ppBGvbk94wHmjKWvka2-TCl9p1xtAWQrev9ybYhPuW9-g7kNB54lUInquj5fwThM1EYh-6RjS9t4V0kUCYR-8AyCYIkF_DgPb7D5VWeEhYmfpaHntNjLk4wmTcDofdZYsRAyTwPGbTCVS_OsUe9N-Cgo84F_UVEnITH-w7s6o2GNkIJwjveyt80Yh-GXLumiYuFmYC7ZNzE4AGV36Staa"

    val playlistTracks = listOf(
        TrackItem(
            id = "pl_after_dark",
            title = "After Dark",
            artist = "Mr.Kitty",
            album = "Time",
            duration = "4:18",
            badge = "FLAC 24-BIT",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuB9EhPaqliYus13wDw1U2SMmGwpqAI8E0m-_yNWHVpzLHwR1UKIPOR0Jx0k0Ro4ZjmwlHRInPHwxhCxGr0iRUyyHdRcg18D3_V3935zLXDE7bo-OE6eCAV-4QUdeN2QkFOEyQbrriYdGHwLTyogAXb0SFg9K0Ni3dyEMNdw0MwC5NhoDu4_ZG9_EDHJ9a5MhhUMVR-4iQTA4bquAZP6g6T41EmIBnLNNy7bnl006GSJp6IO3OhttHRt",
            isLiked = true
        ),
        TrackItem(
            id = "pl_resonance",
            title = "Resonance",
            artist = "HOME",
            album = "Odyssey",
            duration = "4:32",
            badge = "FLAC",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCBE6rXDFIm3bDEf3gSQe_1-xyO6x-wh-GDm0NLCpz5NkGJ2LftBUKW9L-V89wPpofE_Ti9ca0WfeUNIX5CZyoeqi79TG7fJa0prVdMK55cLAtSsqPiIsWCsI_jPbTn8BRNrtGK_F_nCPIWlj-ZjRGU2aA_5NJ6nwtdS4EsdyFQRfR7L9YYZHlCVH1btAa1FUStBQr6zb8A55HcWNeHqwtYoOBq8tWPcTjWcJr-D5eJ2rQQyB0SWnra",
            isDownloaded = true
        ),
        TrackItem(
            id = "pl_nightcall",
            title = "Nightcall",
            artist = "Kavinsky",
            album = "Outrun",
            duration = "4:19",
            badge = "FLAC 24-BIT",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAbnEaCISyUexa2LHBII0DNVN-TbtMYTWLGGShRN2HfRufKjvq_4_xNzmEufMlkeu4VjglkunEjHiHhhJ8ejN-BA0HZCsb5_EPFIi5SryZMsQM5MDL91hNXyP3rSVYKAccPDudafQcAyNxhZEA_piRW3TBhrWFqAHQKsbxJpU_g8FiJCTppvye2yVy_2QV5t8Ve5pxSNXaQ7m0HqP-yhHs1c2C1KIRrmITl-kjs7gX1dDPqiRBlMD3b"
        ),
        TrackItem(
            id = "pl_tech_noir",
            title = "Tech Noir",
            artist = "Gunship",
            album = "Gunship",
            duration = "4:57",
            badge = "320 KBPS",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuC5b1jBEIT2obliAolpPE0QOop3AI_PX2Zie_QReeCK-cLp9ZlU4aqHZdaVdnRyqtEx8_nBfVPuTI1nLjtBFO_m_iA9p5uASnBxErRXIJaG5d4xlUocWLB3GhQky6mC-2ByFYCTN9MxaNkBGSQCcvfBV0u3KHISRObJReUKSr0ItCl8hx-IZ6VqvSNiCIbD7rJBcdZr5t1w8nTgtfwzizsNGVkeI0mFDDZolU2OVAeBg4UYGQak2Uhh",
            isHiResBadgeCyan = false
        ),
        TrackItem(
            id = "pl_turbo_killer",
            title = "Turbo Killer",
            artist = "Carpenter Brut",
            album = "Trilogy",
            duration = "3:28",
            badge = "FLAC",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAt5clbvoUFtbYXraH_GgeR2Li3Z8uZoVgaymRbkKt6xzYBD1g8kkSRFUROGcRt1ie84w0dgMcu9m9Fc_gn6DNwI16RT-Coer14jZzYf1TBHc5b5GHDxh7Rgd1CQLW1EAa-NFWoxpBpVcKCrkuXgmJU0NT3mdwTR4s8xpdeKR0Ith-c4F52pdtkzFqEA828mcnwyKPDbuQ8R3g-hgmpAYTqEiaSA1G_nXWK2qR01RVh-16WC4oIVqOf",
            isDownloaded = true
        ),
        TrackItem(
            id = "pl_days_of_thunder",
            title = "Days of Thunder",
            artist = "The Midnight",
            album = "Days of Thunder",
            duration = "5:19",
            badge = "FLAC",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuARg5zc8W3CYsnRHKKL5wx8bqMmihqKxYJAENKNeiWr89TVu3g4j4k0bG64G4UM8dzCdniljUvfzEhKzHCVpsVfuypsa4spYmgc8MwPFUE96oJ2bYCTvFxOstZZoBuqqOT8mE1QIlj4F6xXM4rzOF-_Qr1fAhPWp5RMSnCn3-uHS4M_46QxsepJ5xsYyxyyA4ug41iPdWCaZ943_aYaj5wfg0UinXR0I2qnyxXkT6mRliJEi3sT6zT9"
        ),
        TrackItem(
            id = "pl_vessel",
            title = "Vessel",
            artist = "Perturbator",
            album = "The Uncanny Valley",
            duration = "4:44",
            badge = "FLAC 24-BIT",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuB6JFiM8STh6_vAimEu1utWFkhaOFsvLXd1Df4By6JSy0K3Sy9GiOzM7XffNi8ofe0ftv4KIsSpTfLmYbLNh2aax26FgYO1UOqtZm78PzbI1qKZVl6SXO3FCGsP_JSgWMGJIlSnMLezHK2FNIpE1XT_uY7Dn74XhQE0hnE1nRbDrW8nl4KeTjW6EHIzCcDVA5wJFZfZq4pY2nehTzITRWUKmYs5lhkLnX4DHWhyPZGRh-7tYo_W7xT3"
        ),
        TrackItem(
            id = "pl_sunset",
            title = "Sunset",
            artist = "The Midnight",
            album = "Endless Summer",
            duration = "5:26",
            badge = "FLAC",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCKQAchsgZd2bmCbAVr7ojwaOW2kQL5LdVMX8WjcoFkgdflYJmMk2K80drHvIlz8MgPyw8J117g8Bj3vcakiMXDSb08ICUizZlWZSPsSYC62DZyOCN7OddVRkFL1qlnD_effvXc43Y4nuziAIq_ppfUjDfgM8d_DPs4Md4jwAZpiUG7MqmKAvhhzmK61YNMyt5esXJxmlnL0qlRurDjhrS2j_Kj4t6Pb7PuO8LfHxPdLY09G_JFawup"
        )
    )

    // Library Playlists (Image_18 / Image_19)
    val initialLibraryPlaylists = listOf(
        PlaylistItem(
            id = "lib_synthwave_nights",
            title = "Synthwave Nights",
            subtitle = "Navidrome Synced",
            trackCount = "64 skladieb",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBjguji4ZkE3ECuG3ikOsPR6pKw5pJmRi-Zix2tf-wznus1JadmWt9gdmP5l-CKMkqhxJu7H5dy-XOkLWYpzNsioo9qmO0P25586J3YmspszWnz37OeckNrgXbz04yHxa6DaeWSRj5S06vbYOZbYC4QlnEpxiTdDqoxT6Gk_2EmUo_oXzfZvMt0L5LZ9jjaCouoz_dvOWtbBCpVBTBabrE7YwsyyhQllQM4I99cDU1RTMvT9OXHpYzw",
            isPinned = true,
            isSynced = true
        ),
        PlaylistItem(
            id = "lib_cyberpunk_focus",
            title = "Cyberpunk Focus & Flow",
            subtitle = "Playlist",
            trackCount = "112 skladieb",
            durationOrExtra = "6 hod 20 min",
            badge = "HQ",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAoIY_Gt56thl5E-7LPKt4W9yzUkRVoxl4xm6DYBnmO6rk99qP0_ATJrtVukq70R7bqcEuNxo2vVNlORGQ5kFSFDfclGzvSXfg3Z5oc8CJNv_j9wbtBJHSDqwuDMm5btroFOulvEZ_PWiyrEnjmxTzcSALOY0dsnvL-tqCL1cZ4nTWxQ0lA6w6UGrht3Gf-u7L6XE4grIWvglISqUPTp_yBvhx_07GVHqMl53wuMTJCuntaUXno0nOA"
        ),
        PlaylistItem(
            id = "lib_midnight_lofi",
            title = "Midnight Lo-Fi",
            subtitle = "Osobné rádio",
            trackCount = "45 skladieb",
            badge = "OFFLINE",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuD-V9vzN1VpFVhDvUoM7S23F_MQDSfjEsj-m_GdgMXaFcf7dlrRjWTpMVFYo7Q2YsVHQtooOEF0iyy0uW4cjIDXu0pAblqgT8lBSGNVlEmKwqu3d1Wf4_zuDN9FOTuLBNHR9PF5Cl3ZxR4ZN_EdI25pZfZmy9gCud4SPfSRhP3T7be-rMZTpmmme58tucqgrilXydQNdJIpvM9Od-bNx2RVGTPfhRMN6bS3ktwdFdmIw4-E1ATDP9i_",
            isOffline = true
        ),
        PlaylistItem(
            id = "lib_lossless_vault",
            title = "Lossless Audio Vault",
            subtitle = "Navidrome Master",
            trackCount = "88 skladieb",
            badge = "FLAC 24b",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAzLsvPHbX1hiD-acZhYE--3ObPP9oF3Gj1uyOEO0vLq9bx3x5clzZW-K2yunjmoqfis35Yt8Q5WqmJba2p9jBRWxEHmaTZv6hrtYFL5ffUxZWSN59TbutH9ATw4crmSm5pg6S4CRD_1cVLDzqD4ag--bgeP8yNLuwPYhrSi71em2ixBv8wEqVJGNDUYDGeFzD174XmCYA95vO8k1IdJruxATSFlf1v7Ln1d2y2m_Sx4AUAHFQNK4-T",
            isStarred = true
        ),
        PlaylistItem(
            id = "lib_retro_wave_80s",
            title = "Retro Wave 80s Classics",
            subtitle = "Kompilácia",
            trackCount = "50 skladieb",
            imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDpkifyVSziH-FQB4yRCkBZtM-gcO_0GLmvZpo9t1VJnGePzQGFwtxrhw4H1Mm8viveWfr5ZC74hHgSihORBTdu8JhqxL0UfSUtZeeA8RypEgky7m8e6KFP38y8oMCqLWTf18gOcTJdAQR6uoyxRToArjKrRtTnApvuxHJa25vbLy3bEG_-QOa3jfSKwSOdUSGXuA-wB-TocNwA1Ee7z5SpIh1BQvnFLtgiYKd9zjDmqFCFi-a1p5Lk"
        )
    )

    // Now Playing Artwork (Image_7 / Image_8)
    const val NOW_PLAYING_ARTWORK_URL = "https://lh3.googleusercontent.com/aida-public/AB6AXuD8xI_uWZnQjx3HSVW_umJP3H4xMpgjhf_ylaXWDbq3BDqXU3N--20oYXUY4-mlCMnQBF2-5XMARfYy_TEQbGn6JyQfX7c-6okrxUJ_2ovq5KFAgVn9j5IjYpvH1ZSJapA1x2bFohUAQbGo_f8lJ4fgeaHIJURXrVQCtPExcHIOphDm3MZWZunY7338v5XfWWSGhgS4zF-1VbU06puRiRg4E8CX7oqxbdjxUqJHIHV6DJBZogCPUd38"

    // 10-Band EQ Metadata (Image_5 / Image_6)
    val eqBands = listOf(
        EqBandInfo(0, "31", "Sub", "31 Hz"),
        EqBandInfo(1, "62", "Bas", "62 Hz"),
        EqBandInfo(2, "125", "Punch", "125 Hz"),
        EqBandInfo(3, "250", "Warmth", "250 Hz"),
        EqBandInfo(4, "500", "Body", "500 Hz"),
        EqBandInfo(5, "1k", "Vocal", "1 kHz"),
        EqBandInfo(6, "2k", "Clarity", "2 kHz"),
        EqBandInfo(7, "4k", "Presence", "4 kHz"),
        EqBandInfo(8, "8k", "Shimmer", "8 kHz"),
        EqBandInfo(9, "16k", "Air", "16 kHz")
    )

    val eqPresets: Map<String, List<Float>> = linkedMapOf(
        "Synthwave / Retrowave" to listOf(4.5f, 3.0f, 1.5f, 0.0f, -1.5f, -1.0f, 1.0f, 2.5f, 4.0f, 5.0f),
        "Elektronika & Bass Boost" to listOf(6.0f, 5.0f, 3.5f, 1.0f, -0.5f, 0.0f, 1.5f, 3.0f, 4.5f, 4.0f),
        "Audiofil Flat (Referenčný)" to listOf(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f),
        "Vokály & Akustika" to listOf(-2.0f, -1.0f, 0.0f, 1.5f, 3.0f, 3.5f, 2.5f, 1.0f, 0.5f, 1.0f),
        "Nočné počúvanie (Loudness)" to listOf(4.0f, 2.5f, 1.0f, 0.0f, -1.0f, -1.0f, 0.0f, 1.5f, 3.0f, 4.0f)
    )

    // AutoEQ Headphone Profiles (Image_15 / Image_16)
    val headphoneProfiles = listOf(
        HeadphoneProfile(
            id = "hd600",
            name = "Sennheiser HD 600",
            targetInfo = "Harman Target v2 • Oratory1990 meranie",
            preampDb = "-4.8 dB",
            filtersSpec = "10 PEQ",
            impedanceSpec = "300 Ω"
        ),
        HeadphoneProfile(
            id = "hd650",
            name = "Sennheiser HD 650 / 6XX",
            targetInfo = "Harman Target v2 • Crinacle meranie",
            preampDb = "-5.2 dB",
            filtersSpec = "10 PEQ",
            impedanceSpec = "300 Ω"
        ),
        HeadphoneProfile(
            id = "hd800s",
            name = "Sennheiser HD 800 S",
            targetInfo = "Harman Target • Oratory1990 meranie",
            preampDb = "-6.1 dB",
            filtersSpec = "High-End Open Back",
            impedanceSpec = "300 Ω"
        ),
        HeadphoneProfile(
            id = "hd560s",
            name = "Sennheiser HD 560S",
            targetInfo = "Harman Over-Ear Target • Referenčný profil",
            preampDb = "-3.5 dB",
            filtersSpec = "10 PEQ",
            impedanceSpec = "120 Ω"
        ),
        HeadphoneProfile(
            id = "wh1000xm5",
            name = "Sony WH-1000XM5",
            targetInfo = "Bluetooth ANC • Harman Target (Oratory1990)",
            preampDb = "-4.0 dB",
            filtersSpec = "Vypnutý interný EQ",
            impedanceSpec = "48 Ω"
        )
    )
}
