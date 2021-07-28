package app.mosquito.appmosquito.appmosquito.Posts;


public enum EstadoAtual {

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
