//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.football.Domain.League;

import com.football.Domain.Game.Team;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class FixtureGenerator {
    public FixtureGenerator() {
    }

    public List<List<Fixture>> getFixtures(List<Team> teams, boolean includeReverseFixtures) {
        int numberOfTeams = teams.size();
        boolean ghost = false;
        if (numberOfTeams % 2 != 0) {
            ++numberOfTeams;
            ghost = true;

        }


        int totalRounds = numberOfTeams - 1;
        int matchesPerRound = numberOfTeams / 2;
        List<List<Fixture>> rounds = new LinkedList();

        int match;
        int roundNumber;
        for(int round = 0; round < totalRounds; ++round) {
            List<Fixture> fixtures = new LinkedList();

            for(match = 0; match < matchesPerRound; ++match) {
                roundNumber = (round + match) % (numberOfTeams - 1);
                int away = (numberOfTeams - 1 - match + round) % (numberOfTeams - 1);
                if (match == 0) {
                    away = numberOfTeams - 1;
                }

                fixtures.add(new Fixture(teams.get(roundNumber), teams.get(away)));
            }

            rounds.add(fixtures);
        }

        List<List<Fixture>> interleaved = new LinkedList();
        int evn = 0;
        match = numberOfTeams / 2;

        for(roundNumber = 0; roundNumber < rounds.size(); ++roundNumber) {
            if (roundNumber % 2 == 0) {
                interleaved.add(rounds.get(evn++));
            } else {
                interleaved.add(rounds.get(match++));
            }
        }

        rounds = interleaved;

        for(roundNumber = 0; roundNumber < rounds.size(); ++roundNumber) {
            if (roundNumber % 2 == 1) {
                Fixture fixture = (Fixture)((List)rounds.get(roundNumber)).get(0);
                ((List)rounds.get(roundNumber)).set(0, new Fixture(fixture.getAwayTeam(), fixture.getHomeTeam()));
            }
        }

        if (includeReverseFixtures) {
            List<List<Fixture>> reverseFixtures = new LinkedList();
            Iterator var21 = rounds.iterator();

            while(var21.hasNext()) {
                List<Fixture> round = (List)var21.next();
                List<Fixture> reverseRound = new LinkedList();
                Iterator var15 = round.iterator();

                while(var15.hasNext()) {
                    Fixture fixture = (Fixture)var15.next();
                    reverseRound.add(new Fixture(fixture.getAwayTeam(), fixture.getHomeTeam()));
                }

                reverseFixtures.add(reverseRound);
            }

            rounds.addAll(reverseFixtures);
        }

        return rounds;
    }
}
