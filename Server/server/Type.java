package server;
/*
    Author: Haolin Zhang
    File: Type.java
    Date: July 19, 2022
    Ver: 1.5

    Description: Defined all possible use cases for Message
 */

public enum Type {
	Undefine,
    Succeed,
    Failed,
    Login,
    Logout,
    ViewPlayerInfo,
    ShowPlayerInfo,
    GetLobbyManagerInfo,
    ShowLobbyManagerInfo,
    ViewLobby,
    ShowLobby,
    GetBalance,
    ShowBalance,
    ReloadBalance,
    UpdateBalance,
    CreateLobby,
    DeleteLobby,
    JoinLobby,
    ExitLobby,
    StartGame,
    Bet,
    Hit,
    Stay,
    Exit
}
