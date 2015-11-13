package com.bnavarro.pick5football;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class MatchTest {

    private Match match;

    @Test
    public void testCreate(){
        match = new Match(createTeam("Chicago Bears"), createTeam("Green Bay Packers"));
        assertEquals("Team 1 is Chicago Bears", "Chicago Bears", match.getTeam1().getName());
        assertEquals("Team 2 is Green Bay Packers", "Green Bay Packers", match.getTeam2().getName());
    }

    @Test
    public void testGetAndSetTeam(){
        init_setup();
        match.setTeam1(createTeam("Carolina Panthers"));
        match.setTeam2(createTeam("Pittsburgh Steelers"));

        assertEquals("Team 1 is Carolina Panthers", "Carolina Panthers", match.getTeam1().getName());
        assertEquals("Team 2 is Pittsburgh Steelers", "Pittsburgh Steelers", match.getTeam2().getName());
    }

    @Test
    public void tetGetAndSetSelectedTeam(){
        init_setup();
        match.setSelectedTeam("Chicago Bears");
        assertEquals("Selected Team is Chicago Bears", "Chicago Bears", match.getSelectedTeam());

        match.setSelectedTeam(null);
        assertNull("Selected Team is null", match.getSelectedTeam());
    }

    @Test
    public void testGetAndSetHomeTeam (){
        init_setup();

        match.setHomeTeam("Green Bay");
        assertEquals("Home Team is Green Bay Packers", "Green Bay", match.getHomeTeam());

        match.setHomeTeam(null);
        assertNull("Home Team is null", match.getHomeTeam());
    }

    @Test
    public void testGetAndSetMatchDate(){
        init_setup();

        match.setMatchDate("09/10/2015");
        assertEquals("Match Date is 09/10/2015", "09/10/2015", match.getMatchDate());

        match.setMatchDate(null);
        assertNull("Match Date is null", match.getMatchDate());
    }

    @Test
    public void testGetAndSetMatchTime(){
        init_setup();

        match.setMatchTime("1:00 PM ET");
        assertEquals("Match Time is 1:00 PM ET", "1:00 PM ET", match.getMatchTime());

        match.setMatchTime(null);
        assertNull("Match Time is null", match.getMatchTime());
    }

    @Test
    public void testGetTeamDetails_Default(){
        init_setup();

        assertEquals("", "Chicago Bears", match.getTeamHeaderDetails(match.getTeam1()));
    }

    @Test
    public void testGetTeamHeaderDetails_HomeTeam(){
        init_setup();

        match.setHomeTeam("Chicago");
        assertEquals("", "Chicago Bears\n\tAt Home", match.getTeamHeaderDetails(match.getTeam1()));
    }

    @Test
    public void testGetTeamHeaderDetails_FavoredTeamWithSpread(){
        init_setup();

        match.setFavoredTeam("Chicago", 5.0);
        assertEquals("", "Chicago Bears\n\tFavored by 5.0", match.getTeamHeaderDetails(match.getTeam1()));
    }

    @Test
    public void testGetTeamHeaderDetails_FavoredTeamWithNoSpread(){
        init_setup();

        match.setFavoredTeam("Chicago", null);
        assertEquals("", "Chicago Bears\n\tFavored by ", match.getTeamHeaderDetails(match.getTeam1()));
    }

    @Test
    public void testGetTeamHeaderDetails_FavoredTeamWithZeroSpread(){
        init_setup();

        match.setFavoredTeam("Chicago", 0.0);
        assertEquals("", "Chicago Bears\n\tFavored by ", match.getTeamHeaderDetails(match.getTeam1()));
    }

    @Test
    public void testGetTeamHeaderDetails_HomeTeamAndFavoredTeam(){
        init_setup();

        match.setHomeTeam("Chicago");
        match.setFavoredTeam("Chicago", 5.0);
        assertEquals("", "Chicago Bears\n\tAt Home, Favored by 5.0", match.getTeamHeaderDetails(match.getTeam1()));
    }


    private Team createTeam (String name){
        return new Team(name);
    }

    private void init_setup (){
        match = new Match(createTeam("Chicago Bears"), createTeam("Green Bay Packers"));
    }
}