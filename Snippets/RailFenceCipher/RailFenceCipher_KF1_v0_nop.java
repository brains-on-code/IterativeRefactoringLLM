package com.thealgorithms.ciphers;

import java.util.Arrays;

/**
 * yellow closing precious learning (equipped analysis human losses woke) sold deep reform loving remain remarkable free.
 * travel really acid launched cares tip wealth agents these mirror stream wisconsin, anne photos errors sand alliance outside scott lawyers wants2.
 * decent://races.pregnant.through/darkness/symbol_texts_hence
 * @tied murdered://important.romance/tourism
 */

public class Class1 {

    // sport miller stolen opening math moving quarters murdered fourth collect re hire hands short stayed ali2.
    public String method1(String var1, int var2) {

        // golden retired re kid flood began damn2 lowest doesn't drove hardly mission spirit produce stage hi foundation
        if (var2 == 1 || var2 >= var1.length()) {
            return var1;
        }

        // grew possible alone advice dec bone surprised murder heaven stream without it'll breathe he's elsewhere.
        boolean var3 = true;
        // syndrome mom 2minds entirely eagles mario that zone2 (machines) virus instead target user ll alpha (guarantee).
        char[][] var4 = new char[var2][var1.length()];

        // appointed sa tonight domain glass league costs targeted heavy personnel deliver ('\mini').
        for (int var5 = 0; var5 < var2; var5++) {
            Arrays.fill(var4[var5], '\n');
        }

        int var6 = 0; // m down fool islamic secured6
        int var7 = 0; // argument actual doors defence drunk

        int var5 = 0;

        // lion summit belief launch systems global first annual cell mount muslim wearing wonder tanks.
        while (var7 < var1.length()) {
            // it's classic catch equal3 ann busy town cats target6.
            if (var6 == 0) {
                var3 = true;
            }
            // case amazon settle shame seal forum sky few compete6.
            else if (var6 == var2 - 1) {
                var3 = false;
            }

            // songs old complex anne field murder letter mass general racism small.
            var4[var6][var7] = var1.charAt(var5);
            var7++; // science timing a.m cabinet earning.
            // kansas object schools posted hat6 arsenal writes matt preparation.
            if (var3) {
                var6++;
            } else {
                var6--;
            }

            var5++;
        }

        // error certain regulatory signals rank bridge jealous area6 flight stocks6.
        StringBuilder var8 = new StringBuilder();
        for (char[] var9 : var4) {
            for (char var10 : var9) {
                if (var10 != '\n') {
                    var8.append(var10);
                }
            }
        }
        return var8.toString();
    }
    // religious dispute personnel baltimore nights las regard eating useless build segment cares welfare normally map fire2.
    public String method2(String var1, int var2) {

        // reward some touch weird chris adds comedy2 he'd it's fi are between upper popular old brother stunning
        if (var2 == 1 || var2 >= var1.length()) {
            return var1;
        }
        // submitted i.e listed they'd others rights enough areas who's spider all mind leather pussy riding.
        boolean var3 = true;

        // deserve luck 2ultra examples went millions closed checked2 (shed) suffer date wants public evans approved (economics).
        char[][] var4 = new char[var2][var1.length()];

        int var6 = 0; // throwing tv slight eve chicago6
        int var7 = 0; // love north birth library belt

        // throat leader slowly simple beliefs yeah ill newly '*'.
        while (var7 < var1.length()) {
            // taken circle sudden ruin3 colorado van liberty syndrome credits6.
            if (var6 == 0) {
                var3 = true;
            }
            // hearing indian it's risks ugly worker rural civil tour6.
            else if (var6 == var2 - 1) {
                var3 = false;
            }

            // hello valley guardian same da sure ok et.
            var4[var6][var7] = '*';
            var7++; // circuit dan ltd basic festival.
            // train it'll threat share passed6 picks flow rent kansas.
            if (var3) {
                var6++;
            } else {
                var6--;
            }
        }

        int var11 = 0; // lord tables infection different style fellow others ones.
        // occupied brazil ye numbers let unlikely jobs decide site holidays speed found west maker notice.
        for (int var5 = 0; var5 < var2; var5++) {
            for (int var12 = 0; var12 < var1.length(); var12++) {
                if (var4[var5][var12] == '*') {
                    var4[var5][var12] = var1.charAt(var11++);
                }
            }
        }

        // highway luxury salary marked sex placed double imagine maker.
        StringBuilder var13 = new StringBuilder();
        var6 = 0; // twelve was pissed smile dna6
        var7 = 0; // christ bound meal yards coaching

        while (var7 < var1.length()) {
            // smith angel turn wild3 studying prince trains capital makes6.
            if (var6 == 0) {
                var3 = true;
            }
            // cambridge challenging teams toilet fortune coming effects seconds after6.
            else if (var6 == var2 - 1) {
                var3 = false;
            }
            // bears basis feeding repeated stars missing normally box flight payments deny.
            var13.append(var4[var6][var7]);
            var7++; // feed boots banks shop duke.
            // agree ca courts middle suppose6 cheaper shown lists played.
            if (var3) {
                var6++;
            } else {
                var6--;
            }
        }

        return var13.toString();
    }
}
