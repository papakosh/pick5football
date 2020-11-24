package com.bnavarro.pick5football;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by navman on 11/11/2015.
 */
public class TeamTest {

    private Team team;

    @Test
    public void testCreate (){
        Team team = new Team ("Chicago Bears");
        assertEquals("Name is Chicago Bears", "Chicago Bears", team.getName());
    }

    @Test(expected= IllegalArgumentException.class)
    public void testGetNFLCode_MissingTeamName(){
        team = new Team (null);
        team.getNFLCode();
    }

    @Test(expected= IllegalArgumentException.class)
    public void testGetNFLCode_InvalidTeamName(){
        team = new Team ("Ontario Dolphins");
        team.getNFLCode();
    }

    @Test
    public void testGetNFLCode (){
        team = new Team("Arizona Cardinals");
        assertEquals("NFL Code is ARI", "ARI", team.getNFLCode());

        team = new Team("Detroit Lions");
        assertEquals("NFL Code is DET", "DET", team.getNFLCode());

        team = new Team("Chicago Bears");
        assertEquals("NFL Code is CHI", "CHI", team.getNFLCode());

        team = new Team("Dallas Cowboys");
        assertEquals("NFL Code is DAL", "DAL", team.getNFLCode());

        team = new Team("Philadelphia Eagles");
        assertEquals("NFL Code is PHI", "PHI", team.getNFLCode());

        team = new Team("San Francisco 49ers");
        assertEquals("NFL Code is SF", "SF", team.getNFLCode());

        team = new Team("Seattle Seahawks");
        assertEquals("NFL Code is SEA", "SEA", team.getNFLCode());

        team = new Team("Baltimore Ravens");
        assertEquals("NFL Code is BAL", "BAL", team.getNFLCode());

        team = new Team("Los Angeles Chargers");
        assertEquals("NFL Code is LAC", "LAC", team.getNFLCode());

        team = new Team("Buffalo Bills");
        assertEquals("NFL Code is BUF", "BUF", team.getNFLCode());

        team = new Team("Cleveland Browns");
        assertEquals("NFL Code is CLE", "CLE", team.getNFLCode());

        team = new Team("Houston Texans");
        assertEquals("NFL Code is HOU", "HOU", team.getNFLCode());

        team = new Team("Tennessee Titans");
        assertEquals("NFL Code is TEN", "TEN", team.getNFLCode());

        team = new Team("Indianapolis Colts");
        assertEquals("NFL Code is IND", "IND", team.getNFLCode());

        team = new Team("Washington Football Team");
        assertEquals("NFL Code is WAS", "WAS", team.getNFLCode());

        team = new Team("Jacksonville Jaguars");
        assertEquals("NFL Code is JAC", "JAC", team.getNFLCode());

        team = new Team("New York Giants");
        assertEquals("NFL Code is NYG", "NYG", team.getNFLCode());

        team = new Team("Minnesota Vikings");
        assertEquals("NFL Code is MIN", "MIN", team.getNFLCode());

        team = new Team("Carolina Panthers");
        assertEquals("NFL Code is CAR", "CAR", team.getNFLCode());

        team = new Team("Pittsburgh Steelers");
        assertEquals("NFL Code is PIT", "PIT", team.getNFLCode());

        team = new Team("New Orleans Saints");
        assertEquals("NFL Code is NO", "NO", team.getNFLCode());

        team = new Team("Los Angeles Rams");
        assertEquals("NFL Code is LAR", "LAR", team.getNFLCode());

        team = new Team("Las Vegas Raiders");
        assertEquals("NFL Code is LAV", "LAV", team.getNFLCode());

        team = new Team("Tampa Bay Buccaneers");
        assertEquals("NFL Code is TB", "TB", team.getNFLCode());

        team = new Team("Cincinnati Bengals");
        assertEquals("NFL Code is CIN", "CIN", team.getNFLCode());

        team = new Team("Atlanta Falcons");
        assertEquals("NFL Code is ATL", "ATL", team.getNFLCode());

        team = new Team("Green Bay Packers");
        assertEquals("NFL Code is GB", "GB", team.getNFLCode());

        team = new Team("New England Patriots");
        assertEquals("NFL Code is NE", "NE", team.getNFLCode());

        team = new Team("Kansas City Chiefs");
        assertEquals("NFL Code is KC", "KC", team.getNFLCode());

        team = new Team("Denver Broncos");
        assertEquals("NFL Code is DEN", "DEN", team.getNFLCode());

        team = new Team("New York Jets");
        assertEquals("NFL Code is NYJ", "NYJ", team.getNFLCode());

        team = new Team("Miami Dolphins");
        assertEquals("NFL Code is MIA", "MIA", team.getNFLCode());
    }

    @Test(expected= IllegalArgumentException.class)
    public void testGetLogo_ExceptionThrown(){
        team = new Team (null);
        team.getLogo();
    }

    @Test(expected= IllegalArgumentException.class)
    public void testGetLogo_InvalidTeamName(){
        team = new Team ("Ontario Dolphins");
        team.getLogo();
    }

    @Test
    public void testGetLogo (){
        team = new Team("Arizona Cardinals");
        assertNotNull("NFL Logo Id not null", team.getLogo());

        team = new Team("Detroit Lions");
        assertNotNull("NFL Logo Id not null", team.getLogo());

        team = new Team("Chicago Bears");
        assertNotNull("NFL Logo Id not null", team.getLogo());

        team = new Team("Dallas Cowboys");
        assertNotNull("NFL Logo Id not null", team.getLogo());

        team = new Team("Philadelphia Eagles");
        assertNotNull("NFL Logo Id not null", team.getLogo());

        team = new Team("San Francisco 49ers");
        assertNotNull("NFL Logo Id not null", team.getLogo());

        team = new Team("Seattle Seahawks");
        assertNotNull("NFL Logo Id not null", team.getLogo());

        team = new Team("Baltimore Ravens");
        assertNotNull("NFL Logo Id not null", team.getLogo());

        team = new Team("Los Angeles Chargers");
        assertNotNull("NFL Logo Id not null", team.getLogo());

        team = new Team("Buffalo Bills");
        assertNotNull("NFL Logo Id not null", team.getLogo());

        team = new Team("Cleveland Browns");
        assertNotNull("NFL Logo Id not null", team.getLogo());

        team = new Team("Houston Texans");
        assertNotNull("NFL Logo Id not null", team.getLogo());

        team = new Team("Tennessee Titans");
        assertNotNull("NFL Logo Id not null", team.getLogo());

        team = new Team("Indianapolis Colts");
        assertNotNull("NFL Logo Id not null", team.getLogo());

        team = new Team("Washington Football Team");
        assertNotNull("NFL Logo Id not null", team.getLogo());

        team = new Team("Jacksonville Jaguars");
        assertNotNull("NFL Logo Id not null", team.getLogo());

        team = new Team("New York Giants");
        assertNotNull("NFL Logo Id not null", team.getLogo());

        team = new Team("Minnesota Vikings");
        assertNotNull("NFL Logo Id not null", team.getLogo());

        team = new Team("Carolina Panthers");
        assertNotNull("NFL Logo Id not null", team.getLogo());

        team = new Team("Pittsburgh Steelers");
        assertNotNull("NFL Logo Id not null", team.getLogo());

        team = new Team("New Orleans Saints");
        assertNotNull("NFL Logo Id not null", team.getLogo());

        team = new Team("Los Angeles Rams");
        assertNotNull("NFL Logo Id not null", team.getLogo());

        team = new Team("Oakland Raiders");
        assertNotNull("NFL Logo Id not null", team.getLogo());

        team = new Team("Tampa Bay Buccaneers");
        assertNotNull("NFL Logo Id not null", team.getLogo());

        team = new Team("Cincinnati Bengals");
        assertNotNull("NFL Logo Id not null", team.getLogo());

        team = new Team("Atlanta Falcons");
        assertNotNull("NFL Logo Id not null", team.getLogo());

        team = new Team("Green Bay Packers");
        assertNotNull("NFL Logo Id not null", team.getLogo());

        team = new Team("New England Patriots");
        assertNotNull("NFL Logo Id not null", team.getLogo());

        team = new Team("Kansas City Chiefs");
        assertNotNull("NFL Logo Id not null", team.getLogo());

        team = new Team("Denver Broncos");
        assertNotNull("NFL Logo Id not null", team.getLogo());

        team = new Team("New York Jets");
        assertNotNull("NFL Logo Id not null", team.getLogo());

        team = new Team("Miami Dolphins");
        assertNotNull("NFL Logo Id not null", team.getLogo());

    }

}


