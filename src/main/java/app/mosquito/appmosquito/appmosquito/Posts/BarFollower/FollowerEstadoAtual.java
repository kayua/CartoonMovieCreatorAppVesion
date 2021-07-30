package app.mosquito.appmosquito.appmosquito.Posts.BarFollower;


public enum FollowerEstadoAtual {

    FAZENDO {
        @Override
        public String toString() {
            return "fazendo";
        }
    }, FINALIZADO {
        @Override
        public String toString() {
            return "finalizado";
        }
    }
}
