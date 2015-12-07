'use strict';

angular.module('tiimspotApp').controller('EventScoreDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'EventScore',
        function($scope, $stateParams, $modalInstance, entity, EventScore) {

        $scope.eventScore = entity;
        $scope.load = function(id) {
            EventScore.get({id : id}, function(result) {
                $scope.eventScore = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('tiimspotApp:eventScoreUpdate', result);
            $modalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.eventScore.id != null) {
                EventScore.update($scope.eventScore, onSaveSuccess, onSaveError);
            } else {
                EventScore.save($scope.eventScore, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
