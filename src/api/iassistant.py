from abc import ABCMeta, abstractmethod


class IAssistant(metaclass=ABCMeta):

    @abstractmethod
    def train_model(self):
        """ Implemented in child class """

    @abstractmethod
    def request_tag(self, message):
        """ Implemented in child class """

    @abstractmethod
    def get_tag_by_id(self, id):
        """ Implemented in child class """

    @abstractmethod
    def request_method(self, message):
        """ Implemented in child class """

    @abstractmethod
    def request(self, message):
        """ Implemented in child class """

    @abstractmethod
    def request_response(self, message):
        """ Implemented in child class """
